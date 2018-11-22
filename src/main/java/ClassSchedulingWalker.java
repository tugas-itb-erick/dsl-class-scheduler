import classscheduler.models.Classroom;
import classscheduler.models.Clazz;
import classscheduler.models.Course;
import classscheduler.models.DayTime;
import classscheduler.models.Lecturer;
import classscheduler.enums.Day;
import classscheduler.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassSchedulingWalker extends ClassSchedulingParserBaseListener {
    private ClassroomRepository classroomRepository;
    private ClazzRepository clazzRepository;
    private CourseRepository courseRepository;
    private LecturerRepository lecturerRepository;

    public ClassSchedulingWalker(ClassroomRepository classroomRepository,
                                 ClazzRepository clazzRepository,
                                 CourseRepository courseRepository,
                                 LecturerRepository lecturerRepository) {
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
						name = line.map().value().WORD().stream().map(word -> word.toString()).collect(Collectors.joining(" "));
						break;
					case "preferred_courses":
						line.map().value().WORD().forEach(word -> preferredCourses.add(word.toString().replace("_", " ")));
						break;
					case "not_preferred_times":
						line.map().value().map().forEach(map -> {
							Day day = Day.valueOf(map.key().WORD().toString().toUpperCase());
							List<Integer> times = new ArrayList<>();
							map.value().WORD().forEach(time -> times.add(Integer.parseInt(time.toString())));
							notPreferredTimes.add(new DayTime(day, times));
						});
						break;
				}
			}

			Lecturer lecturer = new Lecturer(id, name, notPreferredTimes, preferredCourses);
    		System.out.println(lecturer);
    		lecturerRepository.addLecturer(lecturer);
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
                    	lineContext.map().value().WORD().forEach(facility -> facilities.add(facility.toString().replace("_", " ")));
                }
            }
            Classroom classroom = new Classroom(id, capacity, facilities);
			System.out.println(classroom);
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
			System.out.println(clazz);
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
						name = lineContext.map().value().WORD().stream().map(word -> word.toString()).collect(Collectors.joining(" "));
						break;
					case "credits":
						credits = Integer.parseInt(lineContext.map().value().WORD(0).toString());
						break;
					case "facilities":
						lineContext.map().value().WORD().forEach(word -> facilities.add(word.toString().replace("_", " ")));
						break;
				}
			}
			Course course = new Course(id, name, credits, facilities);
			System.out.println(course);
			courseRepository.addCourse(course);
		}
    }
}
