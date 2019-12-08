package idwall.desafio.string;

import idwall.desafio.string.model.Line;
import idwall.desafio.string.model.Text;
import lombok.extern.java.Log;

import java.util.StringTokenizer;
import java.util.stream.Stream;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
@Log
public class IdwallFormatter extends StringFormatter {

    private static final Boolean DEFAULT_JUSTIFY = false;

    /**
     * Format text with default line limit (40) and not justified.
     *
     * @param text Text to format.
     * @return Formatted text.
     */
    @Override
    public String format(String text) {
        return this.format(text, this.getLimit());
    }

    /**
     * Format text, set up by line limit and not justified.
     *
     * @param text Text to format.
     * @param limit Line limit.
     * @return Formatted text.
     */
    @Override
    public String format(String text, Integer limit) {
        return this.format(text, limit, DEFAULT_JUSTIFY);
    }

    /**
     * Format text, set up by line limit and if it has to justify.
     *
     * @param input Text to format.
     * @param limit Line limit.
     * @param justify Justitied text?
     * @return Formatted text.
     */
    @Override
    public String format(String input, Integer limit, Boolean justify) {

        if (input == null || input.trim().equals("")) {
            throw new IllegalArgumentException("Input can not be null or empty: " + input);
        }

        if (limit <= 0) {
            throw new IllegalArgumentException("Limit has to be greater than zero: " + 0);
        }

        long time = System.currentTimeMillis();

        String[] inputList = input.split("((?<=[\\r?\\n|\\s])|(?=[\\r?\\n|\\s]))");
        Text text = new Text(limit, justify);

        for (String word : inputList) {

            switch (word) {
                case "\n":
                    text.newLine();
                    break;
                case " ":
                    // do nothing
                    break;
                default:
                    text.appendWord(word);
            }

        }

        log.info(String.format("Text formatted successfully in %d ms", System.currentTimeMillis() - time));

        time = System.currentTimeMillis();

        String output = text.toString();

        log.info(String.format("Text generated in %d ms", System.currentTimeMillis() - time));

        return output;
    }

}
