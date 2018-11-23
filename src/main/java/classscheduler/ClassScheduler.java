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
        if (classIndex < 0) {
            return false;
        } else if (classIndex >= clazzRepository.getClasses().size()) {
            return true;
        } else {
            int timeIndex = 0;
            Clazz currentClass = clazzRepository.getClasses().get(classIndex);
            Course currentCourse = courseRepository.getCourses().get(currentClass.getCourseId()); // blm cek null
            String lecturerId = currentClass.getLecturerId();
            List<DayHour> availability = convertDayTimeListToDayHourList(lecturerRepository.getLecturers()
                    .get(lecturerId).getPreferredTimes());
            int availabilitySize = availability.size();
            while (timeIndex < availabilitySize) { // iterasi semua waktu available dosen
                Optional<Classroom> bestClassroom = getBestClassroom(availability.get(timeIndex),
                        currentCourse.getFacilities(), currentClass.getQuota());
                if (bestClassroom.isPresent() &&
                        !isLecturerTeaching(availability.get(timeIndex), lecturerId) &&
                        !isLecturerExceedCreditLimit(availability.get(timeIndex).getDay(), lecturerId)) {
                    Session session = new Session(currentClass, bestClassroom.get());
                    schedule.addSession(availability.get(timeIndex), session);
                    boolean valid = schedulingRecursive(++classIndex);
                    if (valid) {
                        return true;
                    } else {
                        schedule.deleteSession(availability.get(timeIndex), bestClassroom.get().getId());
                    }
                }
                ++timeIndex;
            }
            // semua waktu yg dosen punya ga pas: backtrack here
            return false;
        }
    }

    private List<DayHour> convertDayTimeListToDayHourList(List<DayTime> dayTimes) {
        List<DayHour> dayHours = new ArrayList<>();
        dayTimes.forEach(dayTime -> dayHours.addAll(convertDayTimeToDayHourList(dayTime)));
        return dayHours;
    }

    private List<DayHour> convertDayTimeToDayHourList(DayTime dayTime) {
        List<DayHour> dayHours = new ArrayList<>();
        dayTime.getTimes().forEach(hour -> dayHours.add(new DayHour(dayTime.getDay(), hour)));
        return dayHours;
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

}
