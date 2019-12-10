package salomax.idwall.crawler.view;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import salomax.idwall.crawler.model.SubReddit;

import java.util.stream.Collectors;

/**
 * Created by marcos.salomao.
 */
@Component
@Qualifier("Console")
public class ConsoleSubRedditView implements SubRedditView {

    public String format(SubReddit subReddit) {

        StringBuilder builder = new StringBuilder();

        String patternSub = "\n==========================================\n\nSUBREDDIT %s\n\n";
        String patternThread = "%-7d %s\n        link [%s]\n        comments [%s]\n\n";

        builder.append(String.format(patternSub, subReddit.getName()));

        builder.append(subReddit.getThreadList().stream()
                .sorted((o1, o2) -> o2.getRate().compareTo(o1.getRate()))
                .map(thread -> String.format(patternThread,
                        thread.getRate(),
                        thread.getTitle(),
                        thread.getThreadUrl(),
                        thread.getCommentUrl()))
                .collect(Collectors.joining()));

        builder.append(String.format("\n%d threads found for subreddit %s\n",
                subReddit.getThreadList().size(), subReddit.getName()));

        return builder.toString();
    }

}
