import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.InputStream;

public class Main {

    public static void main(String args[]) throws Exception {
        InputStream is = Main.class.getResourceAsStream("test.txt");
        CharStream stream = CharStreams.fromStream(is);
        ClassSchedulingLexer lexer = new ClassSchedulingLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        ClassSchedulingParser parser = new ClassSchedulingParser(tokens);
        ParseTree tree = parser.createFacility();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk( new ClassSchedulingWalker(), tree );
    }
}
