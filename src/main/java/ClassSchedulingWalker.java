import classscheduler.models.DayTime;
import classscheduler.models.Lecturer;
import classscheduler.repositories.*;

import java.util.List;

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

    public void exitFile(ClassSchedulingParser.FileContext ctx) {
        createLecturer(ctx.createLecturer());
//        for (int i = 0; i < ctx.createLecturer().size(); i++) {
//            String id;
//            String name;
//            List<DayTime> notPreferredTimes =
//            ctx.createLecturer(i).createParam()
//        }
    }

    public void createLecturer(List<ClassSchedulingParser.CreateLecturerContext> ctx) {
    }

    public void createClassroom(List<ClassSchedulingParser.CreateClassroomContext> ctx) {
    }

    public void createClass(List<ClassSchedulingParser.CreateClassContext> ctx) {
    }

    public void createCourse(List<ClassSchedulingParser.CreateCourseContext> ctx) {
    }
}
