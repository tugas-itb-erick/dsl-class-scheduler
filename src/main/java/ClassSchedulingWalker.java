import classscheduler.models.Classroom;
import classscheduler.models.Clazz;
import classscheduler.models.Course;
import classscheduler.models.DayTime;
import classscheduler.models.Lecturer;
import classscheduler.enums.Day;
import classscheduler.repositories.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class ClassSchedulingWalker extends ClassSchedulingParserBaseListener {
    private ClassroomRepository classroomRepository;
    private ClazzRepository clazzRepository;
    private CourseRepository courseRepository;
    private LecturerRepository lecturerRepository;

    public ClassSchedulingWalker(ClassroomRepository classroomRepository,
                                 ClazzRepository clazzRepository,
                                 CourseRepository courseRepository,
                                 LecturerRepository lecturerRepository,
                                 TimeSlotRepository timeSlotRepository) {
        this.classroomRepository = classroomRepository;
        this.clazzRepository = clazzRepository;
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
    }

    public void exitFile(ClassSchedulingParser.FileContext ctx) {
        createLecturer(ctx.createLecturer());
        createClassroom(ctx.createClassroom());
        createClass(ctx.createClass());
		createCourse(ctx.createCourse());
    }

    public void createLecturer(List<ClassSchedulingParser.CreateLecturerContext> ctx) {
    	for (ClassSchedulingParser.CreateLecturerContext context : ctx) {
			String id = "";
			String name = "";
			List<DayTime> notPreferredTimes = new ArrayList<DayTime>();
			List<String> preferredCourses = new ArrayList<String>();

    		for (ClassSchedulingParser.LineContext line : context.createParam().line()) {
				switch (line.map().key().WORD().toString()) {
					case "id":
						id = line.map().value().WORD(0).toString();
						break;
					case "name":
						for (TerminalNode word : line.map().value().WORD()) {
							name = name + " " + word.toString();
						}
						break;
					case "preferred_courses":
						for (TerminalNode word : line.map().value().WORD()) {
							preferredCourses.add(word.toString());
						}
						break;
					case "not_preferred_times":
						for (ClassSchedulingParser.MapContext map : line.map().value().map()) {
							Day day = Day.valueOf(map.key().WORD().toString().toUpperCase());
							List<Integer> times = new ArrayList<>();
							for (TerminalNode word : map.value().WORD()) {
								times.add(Integer.parseInt(word.toString()));
							}
							notPreferredTimes.add(new DayTime(day, times));
						}
						break;
				}
			}

    		lecturerRepository.addLecturer(new Lecturer(id, name, notPreferredTimes, preferredCourses));
		}
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
    	for (ClassSchedulingParser.CreateClassContext createClassContext : ctx) {
			String name = "";
			String courseId = "";
			int quota = 0;
			String lecturerId = "";
			for (ClassSchedulingParser.LineContext lineContext : createClassContext.createParam().line()) {
				switch (lineContext.map().key().WORD().toString()) {
					case "name":
						name = lineContext.map().value().WORD(0).toString();
						break;
					case "course_id":
						courseId = lineContext.map().value().WORD(0).toString();
						break;
					case "quota":
						quota = Integer.parseInt(lineContext.map().value().WORD(0).toString());
						break;
					case "lecturer_id":
						lecturerId = lineContext.map().value().WORD(0).toString();
						break;
				}
			}
			Clazz clazz = new Clazz(name, courseId, quota, lecturerId);
			clazzRepository.addClazz(clazz);
		}
    }

    public void createCourse(List<ClassSchedulingParser.CreateCourseContext> ctx) {
    	for (ClassSchedulingParser.CreateCourseContext createCourseContext : ctx) {
			String id = "";
			String name = "";
			int credits = 0;
			List<String> facilities = new ArrayList<>();
			for (ClassSchedulingParser.LineContext lineContext : createCourseContext.createParam().line()) {
				switch (lineContext.map().key().WORD().toString()) {
					case "id":
						id = lineContext.map().value().WORD(0).toString();
						break;
					case "name":
						name = lineContext.map().value().WORD(0).toString();
						break;
					case "credits":
						credits = Integer.parseInt(lineContext.map().value().WORD(0).toString());
						break;
					case "facilities":
						lineContext.map().value().WORD().forEach(word -> facilities.add(word.toString()));
						break;
				}
			}
			Course course = new Course(id, name, credits, facilities);
			courseRepository.addCourse(course);
		}
    }
}
