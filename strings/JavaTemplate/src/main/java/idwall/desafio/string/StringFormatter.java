package idwall.desafio.string;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public abstract class StringFormatter {

    @Getter(AccessLevel.PROTECTED)
    private Integer limit;

    /**
     * Default constructor with 40 chars as line limit.
     */
    public StringFormatter() {
        this.limit = 40;
    }

    /**
     * Format text with default line limit (40) and not justified.
     *
     * @param text Text to format.
     * @return Formatted text.
     */
    public abstract String format(String text);

    /**
     * Format text, set up by line limit and not justified.
     *
     * @param text Text to format.
     * @param limit Line limit.
     * @return Formatted text.
     */
    public abstract String format(String text, Integer limit);

    /**
     * Format text, set up by line limit and if it has to justify.
     *
     * @param input Text to format.
     * @param limit Line limit.
     * @param justify Justity text?
     * @return Formatted text.
     */
    public abstract String format(String input, Integer limit, Boolean justify);

}
