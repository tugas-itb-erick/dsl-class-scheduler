import classscheduler.ClassScheduler;
import classscheduler.repositories.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.InputStream;

public class Main {

    public static void main(String args[]) throws Exception {
        InputStream is = Main.class.getResourceAsStream("data.txt");
        CharStream stream = CharStreams.fromStream(is);
        ClassSchedulingLexer lexer = new ClassSchedulingLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        ClassSchedulingParser parser = new ClassSchedulingParser(tokens);

        ClassroomRepository classroomRepository = new ClassroomRepository();
        ClazzRepository clazzRepository = new ClazzRepository();
        CourseRepository courseRepository = new CourseRepository();
        LecturerRepository lecturerRepository = new LecturerRepository();

        ParseTree tree = parser.file();
        ParseTreeWalker walker = new ParseTreeWalker();

        ClassSchedulingWalker classSchedulingWalker = new ClassSchedulingWalker(
                classroomRepository,
                clazzRepository,
                courseRepository,
                lecturerRepository);
        walker.walk(classSchedulingWalker, tree);

        ClassScheduler classScheduler = new ClassScheduler(
                classroomRepository,
                clazzRepository,
                courseRepository,
                lecturerRepository);
        classScheduler.startScheduling();
    }
}
