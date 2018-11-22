import classscheduler.repositories.*;

public class ClassSchedulingWalker extends ClassSchedulingParserBaseListener {
    private ClassroomRepository classroomRepository;
    private ClazzRepository clazzRepository;
    private CourseRepository courseRepository;
    private LecturerRepository lecturerRepository;
    private TimeSlotRepository timeSlotRepository;

    public ClassSchedulingWalker(ClassroomRepository classroomRepository,
                                 ClazzRepository clazzRepository,
                                 CourseRepository courseRepository,
                                 LecturerRepository lecturerRepository,
                                 TimeSlotRepository timeSlotRepository) {
        this.classroomRepository = classroomRepository;
        this.clazzRepository = clazzRepository;
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public void exitCreateLecturer(ClassSchedulingParser.CreateLecturerContext ctx) {
//        ctx.createParam().;
    }
}
