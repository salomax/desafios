package idwall.desafio.string;

import idwall.desafio.string.model.Line;
import idwall.desafio.string.style.LineAligment;
import idwall.desafio.string.style.impl.JustifyLineAlignment;
import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by marcos.salomao.
 */
public class JustifyLineUnitTest {

    @Test
    public void testJustifyLine_1() {

        String source = "of God was hovering over the waters.";
        String match = "of  God was  hovering over  the  waters.";
        String[] inputList = source.split("((?<=[\\r?\\n|\\s])|(?=[\\r?\\n|\\s]))");

        LineAligment lineAligment = new JustifyLineAlignment(40);
        Line line = new Line();
        Stream.of(inputList).filter(s -> s.trim().length() != 0).forEach(line::appendWord);

        assertEquals("Justified text is not matching", match, lineAligment.formatStyle(line));

    }

    @Test
    public void testJustifyLine_2() {

        String source = "Test            test  test";
        String match =  "Test       test       test";
        String[] inputList = source.split("((?<=[\\r?\\n|\\s])|(?=[\\r?\\n|\\s]))");

        LineAligment lineAligment = new JustifyLineAlignment(source.length());
        Line line = new Line();
        Stream.of(inputList).filter(s -> s.trim().length() != 0).forEach(line::appendWord);

        assertEquals("Justified text is not matching", match, lineAligment.formatStyle(line));

    }

    @Test
    public void testJustifyLine_3() {

        String source = "Test  test        test test";
        String match =  "Test    test   test    test";
        String[] inputList = source.split("((?<=[\\r?\\n|\\s])|(?=[\\r?\\n|\\s]))");

        LineAligment lineAligment = new JustifyLineAlignment(source.length());
        Line line = new Line();
        Stream.of(inputList).filter(s -> s.trim().length() != 0).forEach(line::appendWord);

        assertEquals("Justified text is not matching", match, lineAligment.formatStyle(line));
    }


}
