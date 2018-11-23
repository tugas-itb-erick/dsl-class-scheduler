import classscheduler.enums.Day;
import classscheduler.models.*;
import classscheduler.repositories.ClassroomRepository;
import classscheduler.repositories.ClazzRepository;
import classscheduler.repositories.CourseRepository;
import classscheduler.repositories.LecturerRepository;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ClassSchedulingWalkerTest {

	private ClassroomRepository classroomRepository;
	private ClazzRepository clazzRepository;
	private CourseRepository courseRepository;
	private LecturerRepository lecturerRepository;
	private ClassSchedulingWalker classSchedulingWalker;

	private HashMap<String, Classroom> classrooms;
	private List<Clazz> classes;
	private HashMap<String, Course> courses;
	private HashMap<String, Lecturer> lecturers;

	@Before
	public void setUp() throws Exception {
		classroomRepository = new ClassroomRepository();
		clazzRepository = new ClazzRepository();
		courseRepository = new CourseRepository();
		lecturerRepository = new LecturerRepository();
		classSchedulingWalker = new ClassSchedulingWalker(
				classroomRepository,
				clazzRepository,
				courseRepository,
				lecturerRepository);

		classrooms = new HashMap<>();
		classrooms.put("7601", new Classroom("7601", 90, Arrays.asList("papan tulis", "lcd", "pc", "microphone")));
		classrooms.put("7606", new Classroom("7606", 55, Arrays.asList("papan tulis", "lcd", "pc", "microphone")));
		classrooms.put("7609", new Classroom("7609", 40, Arrays.asList("papan tulis", "lcd", "microphone")));
		classrooms.put("7610", new Classroom("7610", 40, Arrays.asList("papan tulis", "lcd", "microphone")));
		classrooms.put("7608", new Classroom("7608", 60, Arrays.asList("papan tulis", "lcd", "laptop", "microphone")));
		classrooms.put("7603", new Classroom("7603", 40, Arrays.asList("papan tulis", "lcd", "laptop")));
		classrooms.put("7604", new Classroom("7604", 40, Arrays.asList("papan tulis", "lcd", "laptop")));

		classes = new ArrayList<>();
		classes.add(new Clazz("K1", "IF4150", 50, "123456"));
		classes.add(new Clazz("K2", "IF4150", 55, "987654"));
		classes.add(new Clazz("K3", "IF4150", 50, "235689"));
		classes.add(new Clazz("K1", "IF4071", 50, "246802"));
		classes.add(new Clazz("K2", "IF4071", 50, "124578"));
		classes.add(new Clazz("K1", "IF4090", 80, "134679"));
		classes.add(new Clazz("K1", "IF4072", 55, "864202"));
		classes.add(new Clazz("K2", "IF4072", 55, "124578"));
		classes.add(new Clazz("K1", "IF4070", 50, "135791"));
		classes.add(new Clazz("K1", "IF4043", 40, "135791"));
		classes.add(new Clazz("K1", "IF4040", 45, "129856"));
		classes.add(new Clazz("K1", "IF4073", 40, "975319"));

		courses = new HashMap<>();
		courses.put("IF4150", new Course("IF4150", "Rekayasa Perangkat Lunak Spesifik Domain", 2, Arrays.asList("papan tulis", "lcd"), Arrays.asList("IF4071", "IF4090")));
		courses.put("IF4070", new Course("IF4070", "Representasi Pengetahuan dan Penalaran", 3, Arrays.asList("papan tulis", "lcd", "laptop"), Arrays.asList()));
		courses.put("IF4071", new Course("IF4071", "Pembelajaran Mesin", 3, Arrays.asList("papan tulis", "lcd", "pc"), Arrays.asList("IF4150", "IF4090")));
		courses.put("IF4072", new Course("IF4072", "Pemrosesan Text dan Suara Bahasa Alami", 3, Arrays.asList("papan tulis", "lcd", "microphone", "pc"), Arrays.asList()));
		courses.put("IF4090", new Course("IF4090", "Kerja Praktek", 2, Arrays.asList(), Arrays.asList("IF4071", "IF4150")));
		courses.put("IF4043", new Course("IF4043", "Sistem Informasi Lanjut", 3, Arrays.asList("papan tulis", "lcd"), Arrays.asList()));
		courses.put("IF4040", new Course("IF4040", "Pemodelan Data Lanjut", 3, Arrays.asList("papan tulis", "lcd"), Arrays.asList()));
		courses.put("IF4073", new Course("IF4073", "Interpretasi dan Pengolahan Citra", 3, Arrays.asList("papan tulis", "lcd", "pc"), Arrays.asList()));

		lecturers = new HashMap<>();
		lecturers.put("123456", new Lecturer("123456", "Yudis Dwi", Arrays.asList(new DayTime(Day.MONDAY, Arrays.asList(7, 8, 9)), new DayTime(Day.TUESDAY, Arrays.asList(13, 14, 15)))));
		lecturers.put("987654", new Lecturer("987654", "Adi Mulyanto", Arrays.asList(new DayTime(Day.MONDAY, Arrays.asList(7, 8, 9)), new DayTime(Day.TUESDAY, Arrays.asList(12, 14, 15)))));
		lecturers.put("135791", new Lecturer("135791", "Windy Gambetta", Arrays.asList(new DayTime(Day.TUESDAY, Arrays.asList(10, 11, 14, 15)), new DayTime(Day.WEDNESDAY, Arrays.asList(7, 8, 9, 10)))));
		lecturers.put("246802", new Lecturer("246802", "Masayu", Arrays.asList(new DayTime(Day.WEDNESDAY, Arrays.asList(8, 9, 11, 12)), new DayTime(Day.THURSDAY, Arrays.asList(12, 14, 15)))));
		lecturers.put("975319", new Lecturer("975319", "Iping", Arrays.asList(new DayTime(Day.THURSDAY, Arrays.asList(15, 16, 17)), new DayTime(Day.FRIDAY, Arrays.asList(14, 15, 16)))));
		lecturers.put("864202", new Lecturer("864202", "Ayu", Arrays.asList(new DayTime(Day.TUESDAY, Arrays.asList(13, 15, 16, 17)), new DayTime(Day.WEDNESDAY, Arrays.asList(9, 10, 14, 16)))));
		lecturers.put("124578", new Lecturer("124578", "Dessi Puji", Arrays.asList(new DayTime(Day.MONDAY, Arrays.asList(8, 9, 11, 12, 13)), new DayTime(Day.TUESDAY, Arrays.asList(12, 14, 15)), new DayTime(Day.THURSDAY, Arrays.asList(9, 10, 11, 13)))));
		lecturers.put("235689", new Lecturer("235689", "Bayu", Arrays.asList(new DayTime(Day.MONDAY, Arrays.asList(8, 9, 13, 14, 16)), new DayTime(Day.FRIDAY, Arrays.asList(7, 8, 14, 15)))));
		lecturers.put("134679", new Lecturer("134679", "Yani Widyani", Arrays.asList(new DayTime(Day.WEDNESDAY, Arrays.asList(7, 8, 9, 11, 12, 14)), new DayTime(Day.THURSDAY, Arrays.asList(12, 14, 15, 17)), new DayTime(Day.FRIDAY, Arrays.asList(10, 13, 14)))));
		lecturers.put("129856", new Lecturer("129856", "Fazat", Arrays.asList(new DayTime(Day.MONDAY, Arrays.asList(7, 8, 9)), new DayTime(Day.TUESDAY, Arrays.asList(12, 14, 15)), new DayTime(Day.WEDNESDAY, Arrays.asList(11, 12, 13)), new DayTime(Day.FRIDAY, Arrays.asList(13, 15, 17)))));
	}

	@Test
	public void testParser() throws Exception {
		InputStream is = Main.class.getResourceAsStream("test.txt");
		CharStream stream = CharStreams.fromStream(is);
		ClassSchedulingLexer lexer = new ClassSchedulingLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ClassSchedulingParser parser = new ClassSchedulingParser(tokens);

		ParseTree tree = parser.file();
		ParseTreeWalker walker = new ParseTreeWalker();

		walker.walk(classSchedulingWalker, tree);

		assertEquals(classrooms.toString(), classroomRepository.getClassrooms().toString());
		assertEquals(classes.toString(), clazzRepository.getClasses().toString());
		assertEquals(courses.toString(), courseRepository.getCourses().toString());
		assertEquals(lecturers.toString(), lecturerRepository.getLecturers().toString());
	}
}
