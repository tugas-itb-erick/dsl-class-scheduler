import classscheduler.models.Classroom;
import classscheduler.repositories.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
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
        createClassroom(ctx.createClassroom());
        createClass(ctx.createClass());
		createCourse(ctx.createCourse());
    }

    public void createLecturer(List<ClassSchedulingParser.CreateLecturerContext> ctx) {
    }

    public void createClassroom(List<ClassSchedulingParser.CreateClassroomContext> ctx) {
        for (ClassSchedulingParser.CreateClassroomContext createClassroomContext : ctx) {
            String id = "";
            int capacity = 0;
            List<String> facilities = new ArrayList<>();
            for (ClassSchedulingParser.LineContext lineContext : createClassroomContext.createParam().line()) {
                switch (lineContext.map().key().WORD().toString()) {
                    case "id":
                        id = lineContext.map().value().WORD(0).toString();
                        break;
                    case "capacity":
                        capacity = Integer.parseInt(lineContext.map().value().WORD(0).toString());
                        break;
                    case "facilities":
                        for (TerminalNode word : lineContext.map().value().WORD()) {
                            facilities.add(word.toString());
                        }
                }
            }
            Classroom classroom = new Classroom(id, capacity, facilities);
            classroomRepository.addClassroom(classroom);
        }
    }

    public void createClass(List<ClassSchedulingParser.CreateClassContext> ctx) {
    }

    public void createCourse(List<ClassSchedulingParser.CreateCourseContext> ctx) {
    }
}
