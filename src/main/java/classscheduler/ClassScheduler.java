package classscheduler;

import classscheduler.enums.Day;
import classscheduler.models.*;
import classscheduler.enums.Day;
import classscheduler.repositories.*;

import java.util.*;
import java.util.stream.IntStream;

public class ClassScheduler {
    private static final int CREDIT_LIMIT_PER_DAY = 2;

    private ClassroomRepository classroomRepository;
    private ClazzRepository clazzRepository;
    private CourseRepository courseRepository;
    private LecturerRepository lecturerRepository;
    private TimeSlotRepository timeSlotRepository;
    private Schedule schedule;

    public ClassScheduler(ClassroomRepository classroomRepository, ClazzRepository clazzRepository,
                          CourseRepository courseRepository, LecturerRepository lecturerRepository,
                          TimeSlotRepository timeSlotRepository) {
        this.schedule = new Schedule();
        this.classroomRepository = classroomRepository;
        this.clazzRepository = clazzRepository;
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
        this.timeSlotRepository = timeSlotRepository;
    }


    public void preprocess() {
        List<Clazz> newClasses = clazzRepository.getClasses();
        // sort clazzRepo by lecturer's availability asc
        newClasses.sort(Comparator.comparing(clazz -> {
            Lecturer lecturer = lecturerRepository.getLecturers().get(clazz.getLecturerId());
            return lecturer.getPreferredTimes().size();
        }));
        // duplicate clazzRepos: example [class A 2 credit, class B 3 credit, class C 1 credit] ->
        // [class A, class A, class B, class B, class B, class C]
        List<Clazz> duplicatedClasses = new ArrayList<>();
        newClasses.forEach(clazz -> {
            Course course = courseRepository.getCourses().get(clazz.getCourseId());
            for (int i=0; i<course.getCredits(); i++) {
                duplicatedClasses.add(clazz);
            }
        });
        clazzRepository.setClasses(duplicatedClasses);
    }

    public void startScheduling() {
        preprocess();
        boolean foundSchedule = schedulingRecursive(0);
        if (foundSchedule) {
            System.out.println(schedule.toString());
        } else {
            System.out.println("No schedule can satisfy the constraints.");
        }
    }

    private boolean schedulingRecursive(int classIndex) {
        if (classIndex >= clazzRepository.getClasses().size()) {
            // semua kelas ud masuk ke schedule
            return true;
        } else {
            Clazz currentClass = clazzRepository.getClasses().get(classIndex);
            Course currentCourse = courseRepository.getCourses().get(currentClass.getCourseId()); // blm cek null
            String lecturerId = currentClass.getLecturerId();
            List<DayTime> dayTimes = lecturerRepository.getLecturers().get(lecturerId).getPreferredTimes();
            for (DayTime dayTime : dayTimes) {
                for (Integer time : dayTime.getTimes()) {
                    DayHour dayHour = new DayHour(dayTime.getDay(), time);
                    Optional<Classroom> bestClassroom = getBestClassroom(dayHour,
                            currentCourse.getFacilities(), currentClass.getQuota());
                    if (isLecturerExceedCreditLimit(dayTime.getDay(), lecturerId)) {
                        break; // skip to next day
                    }
                    if (!bestClassroom.isPresent() || isLecturerTeaching(dayHour, lecturerId) /*||
                            isCourseConflict(dayHour, currentCourse)*/) {
                        continue; // skip to next hour
                    }
                    // in here, all constraints are *currently* satisfied
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
        // semua waktu yg dosen punya ga pas, jadi kita backtrack
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
        return Optional.of(copy.get(0)); // get classroom with highest capacity
    }

    private boolean isLecturerExceedCreditLimit(Day day, String lecturerId) {
        long count = IntStream.range(7, 17 + 1).mapToObj(hour -> new DayHour(day, hour))
                .mapToLong(dayHour -> schedule.getSessions().getOrDefault(dayHour, Collections.emptyList())
                        .stream().filter(session -> lecturerId.equals(session.getClazz().getLecturerId()))
                        .count())
                .sum();
        return count >= CREDIT_LIMIT_PER_DAY;
    }

    private boolean isCourseConflict(DayHour dayHour, Course course) {
//        List<Session> sessions = schedule.getSessions().getOrDefault(dayHour, Collections.emptyList());
//        for (Session session : sessions) {
//            Course courseInSchedule = courseRepository.getCourses().get(session.getClazz().getCourseId());
//            for (String courseId : course.getConstrainedCourseIds()) {
//                if (courseInSchedule.getConstrainedCourseIds().contains(courseId)) {
//                    return true;
//                }
//            }
//            for (String courseId : courseInSchedule.getConstrainedCourseIds()) {
//                if (course.getConstrainedCourseIds().contains(courseId)) {
//                    return true;
//                }
//            }
//        }
        return false;
    }

}
