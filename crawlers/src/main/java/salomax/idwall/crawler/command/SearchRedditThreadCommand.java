package salomax.idwall.crawler.command;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import salomax.idwall.crawler.exception.IdWallException;
import salomax.idwall.crawler.service.CrawlerService;
import salomax.idwall.crawler.view.ConsoleSubRedditView;
import salomax.idwall.crawler.view.SubRedditView;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Created by marcos.salomao.
 */
@ShellComponent
@Log
public class SearchRedditThreadCommand {

    @Autowired
    private CrawlerService crawlerService;
    @Autowired
    @Qualifier("Console")
    private SubRedditView subRedditView;

    @ShellMethod("Search most rated Reddit threads (Limit 50 threads per Subreddit or 60 Timeout")
    public String search(
            @ShellOption(help="Subreddit list joined by ';'. askreddit;worldnews;cats") String subReddits,
            @ShellOption(help= "Use multi-thread for seaching") Boolean multithread) {

        if (multithread) {
            System.out.println("Using multi-thread mode");
        }

        log.info("Select subreddits: " + subReddits);

        final AtomicBoolean loading = this.startLoading();

        try {

            long time = System.currentTimeMillis();

            return this.crawlerService.search(subReddits, multithread).stream()
                    .map(this.subRedditView::format)
                    .collect(Collectors.joining("\n")) +
                    String.format("\nTotal search time %d ms", System.currentTimeMillis() - time);

        } catch (IdWallException e) {
            return e.getMessage();
        } finally {
            this.finishLoading(loading);
        }

    }

    private synchronized void finishLoading(AtomicBoolean loading) {
        try {
            loading.set(false);
            wait();
        } catch (InterruptedException e) {
            // do nothing
        }
    }

    private synchronized AtomicBoolean startLoading() {
        final AtomicBoolean loading = new AtomicBoolean(true);
        Thread thread = new Thread(() -> {
            try {
                float i = 0.0f;
                while(loading.get()) {
                    System.out.print(String.format("\rSearching %.1fs", i));
                    Thread.currentThread().sleep(100L);
                    i += 0.1;
                }
                System.out.print("\rDone!              \n");
            } catch (InterruptedException e) {
                // do nothing
            }
            synchronized(this) {
                this.notify();
            }
        });
        thread.start();
        return loading;
    }

}
