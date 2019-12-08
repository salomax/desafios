package idwall.desafio.string.style.impl;

import idwall.desafio.string.model.Line;
import idwall.desafio.string.style.LineAligment;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static idwall.desafio.string.model.TextConstants.*;

/**
 * Created by marcos.salomao.
 */
@RequiredArgsConstructor
public class JustifyLineAlignment implements LineAligment {

    private final Integer limit;

    @Override
    public String formatStyle(Line line) {

        // Find out the line gap
        int gap = limit - line.getLength();

        if (gap < 0) {

            // something goes wrong
            throw new IllegalArgumentException("Line gap is less than zero: " + gap);

        } else if (gap == 0 || line.getWords().size() == 0) {

            // do nothing
            return line.toString();

        } else {

            final List<String> wordsCopy = line.getWords().stream()
                    .filter(w -> w.trim().length() != 0)
                    .collect(Collectors.toList());

            return justify(wordsCopy, gap);
        }
    }

    private static String justify(List<String> words, int gap) {

        int spaceSlots = words.size() - 1;

        if (spaceSlots == 0) {
            return words.stream().collect(Collectors.joining());
        }

        int spacesTotal = ( gap +  spaceSlots) /spaceSlots;
        int spaceJustified = ( gap + spaceSlots ) % spaceSlots;

        StringBuilder result = new StringBuilder();
        List<String> tempWords = words.subList(0, words.size() - 1);
        int j = 0;
        for (String word : tempWords) {

            result.append(word);

            IntStream.range(0, spacesTotal).forEach(value -> result.append(WORD_SEPARATOR));

            // Code below is my nearest understanding about what might be the algorithm to justify the text
            // used on the example (output-parte2.txt)
            if (spaceJustified > 0 && (j++ % 2 == 0 || (spaceSlots - spaceJustified) == 0)) {
                result.append(WORD_SEPARATOR);
                spaceJustified--;
            }

            spaceSlots--;
        }

        // Add last word
        result.append(words.get(words.size() - 1));

        return result.toString();
    }

}
