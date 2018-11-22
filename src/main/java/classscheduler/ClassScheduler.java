package classscheduler;

import classscheduler.models.*;
import classscheduler.models.enums.Day;
import classscheduler.repositories.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public void startScheduling() {
        schedulingRecursive(0);
    }

    public void schedulingRecursive(int index) {

    }

    private boolean isLecturerTeaching(DayHour dayHour, String lecturerId) {
        return schedule.isLecturerExist(dayHour, lecturerId);
    }

    private List<Classroom> getAvailableClassrooms(DayHour dayHour, List<String> facilities) {
        List<Classroom> usedClassrooms = schedule.getAllClassroomsInDayHour(dayHour);
        List<Classroom> copy = new ArrayList<Classroom>(classroomRepository.getClassrooms().values());
        copy.removeIf(usedClassrooms::contains);
        copy.removeIf(classroom -> !classroom.getFacilities().containsAll(facilities));
        return copy;
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
