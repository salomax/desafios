package salomax.idwall.crawler.view;

import org.springframework.stereotype.Component;
import salomax.idwall.crawler.model.SubReddit;

import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by marcos.salomao.
 */
@Component
public class TelegramSubRedditView {

    public void format(SubReddit subReddit, Consumer<String> sendMessage) {

        String patternThread = "%s\nrate %d\n%s\nlink %s\ncomments %s\n\n";
        sendMessage.accept(String.format("\n%d threads found for subreddit %s\n",
                        subReddit.getThreadList().size(), subReddit.getName()));

        subReddit.getThreadList().stream()
                .sorted((o1, o2) -> o2.getRate().compareTo(o1.getRate()))
                .forEach(thread ->
                        sendMessage.accept(String.format(patternThread,
                            subReddit.getName(),
                            thread.getRate(),
                            thread.getTitle(),
                            thread.getThreadUrl(),
                            thread.getCommentUrl())));

    }

}
