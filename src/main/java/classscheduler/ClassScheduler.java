package classscheduler;

import classscheduler.enums.Day;
import classscheduler.models.*;
import classscheduler.repositories.*;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;

public class ClassScheduler {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final int CREDIT_LIMIT_PER_DAY = 2;

    private ClassroomRepository classroomRepository;
    private ClazzRepository clazzRepository;
    private CourseRepository courseRepository;
    private LecturerRepository lecturerRepository;
    private Schedule schedule;

    public ClassScheduler(ClassroomRepository classroomRepository,
						  ClazzRepository clazzRepository,
                          CourseRepository courseRepository,
						  LecturerRepository lecturerRepository) {
        this.schedule = new Schedule();
        this.classroomRepository = classroomRepository;
        this.clazzRepository = clazzRepository;
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
    }

    private void preprocess() {
        List<Clazz> newClasses = clazzRepository.getClasses();
        newClasses.sort(Comparator.comparing(clazz -> {
            Lecturer lecturer = lecturerRepository.getLecturers().get(clazz.getLecturerId());
            return lecturer.getPreferredTimes().size();
        }));
        List<Clazz> duplicatedClasses = new ArrayList<>();
        newClasses.forEach(clazz -> {
            Course course = courseRepository.getCourses().get(clazz.getCourseId());
            for (int i = 0; i<course.getCredits(); i++) {
                duplicatedClasses.add(clazz);
            }
        });
        clazzRepository.setClasses(duplicatedClasses);
    }

    public void startScheduling() {
        preprocess();
        schedulingRecursive(0);
		printSchedule();
    }

    private void printSchedule() {
		Arrays.asList(Day.values()).forEach(day -> {
			System.out.println(ANSI_PURPLE + day.toString().substring(0, 1) + day.toString().substring(1).toLowerCase() + ":\n" + ANSI_RESET);
			IntStream.range(7, 18).forEach(hour -> {
				LocalTime startTime = LocalTime.of(hour,0);
				LocalTime endTime = LocalTime.of(hour + 1, 0);
				String duration = startTime + " - " + endTime;
				List<Session> currentSessions = schedule.getSessions().get(new DayHour(day, hour));
				if (currentSessions == null) {
					System.out.println("\t" + ANSI_BLUE + duration + ANSI_RESET + ":" + ANSI_RED + " No class\n" + ANSI_RESET);
				} else {
					System.out.println("\t" + ANSI_BLUE + duration + ANSI_RESET + ":\n");
					currentSessions.forEach(session -> {
						String courseId = session.getClazz().getCourseId();
						String courseName = courseRepository.getCourses().get(courseId).getName();
						String lecturerId = session.getClazz().getLecturerId();
						String lecturerName = lecturerRepository.getLecturers().get(lecturerId).getName();
						String classroomId = session.getClassroom().getId();
						System.out.println("\t\t\t" + ANSI_GREEN + courseId + " " + courseName + ANSI_RESET);
						System.out.println("\t\t\t" + ANSI_CYAN + "Lecturer: " + lecturerName + ANSI_RESET);
						System.out.println("\t\t\t" + "Classroom: " + classroomId + "\n");
					});
				}
			});
			System.out.println();
		});
	}

    private boolean schedulingRecursive(int classIndex) {
        if (classIndex >= clazzRepository.getClasses().size()) {
            return true;
        } else {
            Clazz currentClass = clazzRepository.getClasses().get(classIndex);
            Course currentCourse = courseRepository.getCourses().get(currentClass.getCourseId());
            String lecturerId = currentClass.getLecturerId();
            List<DayTime> dayTimes = lecturerRepository.getLecturers().get(lecturerId).getPreferredTimes();
            for (DayTime dayTime : dayTimes) {
                for (Integer time : dayTime.getTimes()) {
                    DayHour dayHour = new DayHour(dayTime.getDay(), time);
                    Optional<Classroom> bestClassroom = getBestClassroom(dayHour,
                            currentCourse.getFacilities(), currentClass.getQuota());
                    if (isCourseExceedCreditLimit(dayTime.getDay(), currentCourse.getId())) {
                        break;
                    }
                    if (!bestClassroom.isPresent() || isLecturerTeaching(dayHour, lecturerId) ||
                            isCourseConflict(dayHour, currentCourse)) {
                        continue;
                    }
                    Session session = new Session(currentClass, bestClassroom.get());
                    schedule.addSession(dayHour, session);
                    boolean valid = schedulingRecursive(classIndex + 1);
                    if (valid) {
                        return true;
                    } else {
                        schedule.deleteSession(dayHour, bestClassroom.get().getId());
                    }
                }
            }
        }
        return false;
    }

    private boolean isLecturerTeaching(DayHour dayHour, String lecturerId) {
        return schedule.isLecturerExist(dayHour, lecturerId);
    }

    private Optional<Classroom> getBestClassroom(DayHour dayHour, List<String> facilities, int capacity) {
        List<Classroom> usedClassrooms = schedule.getAllClassroomsInDayHour(dayHour);
        List<Classroom> copy = new ArrayList<>(classroomRepository.getClassrooms().values());
        copy.removeIf(usedClassrooms::contains);
        copy.removeIf(classroom -> !classroom.getFacilities().containsAll(facilities));
        copy.removeIf(classroom -> classroom.getCapacity() < capacity);
        if (copy.isEmpty()) {
            return Optional.empty();
        }
        copy.sort(Comparator.comparingInt(Classroom::getCapacity));
        return Optional.of(copy.get(0));
    }

    private boolean isCourseExceedCreditLimit(Day day, String courseId) {
        long count = IntStream.range(7, 17 + 1).mapToObj(hour -> new DayHour(day, hour))
                .mapToLong(dayHour -> schedule.getSessions().getOrDefault(dayHour, Collections.emptyList())
                        .stream().filter(session -> session.getClazz().getCourseId().equals(courseId))
                        .count())
                .sum();
        return count >= CREDIT_LIMIT_PER_DAY;
    }

    private boolean isCourseConflict(DayHour dayHour, Course course) {
        List<Session> sessions = schedule.getSessions().getOrDefault(dayHour, Collections.emptyList());
        for (Session session : sessions) {
            Course courseInSchedule = courseRepository.getCourses().get(session.getClazz().getCourseId());
            for (String courseId : course.getConstraintCourse()) {
                if (courseInSchedule.getConstraintCourse().contains(courseId)) {
                    return true;
                }
            }
            for (String courseId : courseInSchedule.getConstraintCourse()) {
                if (course.getConstraintCourse().contains(courseId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
