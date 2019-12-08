package idwall.desafio.string.model;

import lombok.Getter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static idwall.desafio.string.model.TextConstants.*;

/**
 * Created by marcos.salomao.
 */
@Log
public class Line {

    private List<String> words = new ArrayList<>();
    @Getter
    private Integer length = 0;

    public List<String> getWords() {
        return Collections.unmodifiableList(this.words);
    }

    public void appendWord(String word) {

        // is first word of the line?
        if (this.words.size() > 0) {
            this.words.add(WORD_SEPARATOR.toString());
            this.length++;
        }

        this.words.add(word);
        this.length += word.length();
    }

    @Override
    public String toString() {
        StringBuilder textResult = new StringBuilder();
        this.words.stream().forEach(word -> textResult.append(word));
        return textResult.toString();
    }

    protected boolean overflow(String word, Integer limit) {
        int overflow = this.getLength();
        if (this.words.size() > 0) {
            // word separator length
            overflow++;
        }
        overflow += word.length();
        return overflow > limit;
    }

}
