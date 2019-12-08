package idwall.desafio.string.model;

import idwall.desafio.string.style.LineAligment;
import idwall.desafio.string.style.impl.JustifyLineAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcos.salomao.
 */
public class Text {

    private final Integer limit;
    private final Boolean justify;
    private List<Line> lines = new ArrayList<>();
    private LineAligment justifyAligment;

    public Text(Integer limit, Boolean justify) {
        this.limit = limit;
        this.justify = justify;
        this.justifyAligment = new JustifyLineAlignment(this.limit);
    }

    public Line newLine() {
        Line line = new Line();
        this.lines.add(line);
        return line;
    }

    public void appendWord(String word) {

        if (word == null || word.trim().equals("")) {
            // something goes wrong
            throw new IllegalArgumentException("Word can not be null or empty: " + word);
        }

        Line line = this.getCurrentLine();

        // If the word is wider than the limit then break it
        if (word.length() <= this.limit) {

            if (line.overflow(word, this.limit)) {
                line = this.newLine();
            }

            line.appendWord(word);

        } else {

            // I don't have any definition for wider words than the limit
            // So I'm just breaking into small parts (as limit size)
            // and adding new lines for them
            this.splitWordInLines(word, this.limit);

        }

    }

    @Override
    public String toString() {
        return this.lines.stream()
                .map(line -> {
                    if (this.justify) {
                        return this.justifyAligment.formatStyle(line);
                    } else {
                        return line.toString();
                    }
                })
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private void splitWordInLines(String word, Integer limit) {
        for (int i = 0; i < word.length(); i += limit) {
            String wordFragment = word.substring(i, Math.min(i + limit, word.length()));
            this.newLine().appendWord(wordFragment);
        }
    }

    private Line getCurrentLine() {
        if (this.lines.size() == 0) {
            this.newLine();
        }
        return this.lines.get(lines.size() - 1);
    }

}
