package salomax.idwall.crawler.service.impl;

import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import salomax.idwall.crawler.exception.IdWallException;
import salomax.idwall.crawler.exception.IdWallRuntimeException;
import salomax.idwall.crawler.model.RedditThread;
import salomax.idwall.crawler.service.Crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by marcos.salomao.
 */
@Component
@Qualifier("multiThreadCrawler")
@Scope("prototype")
@Log
public class MultiThreadRedditCrawler extends AbstractRedditCrawler implements Crawler<RedditThread> {

    @Value("${reddit.executor.thread-pool:5}")
    private Integer threadPool;
    @Value("${reddit.executor.timeout:60}")
    private Integer timeout;
    private ExecutorService executorService;

    @Override
    public List<RedditThread> search(String url) throws IdWallException {

        log.fine("Start searching threads at " + url);

        this.executorService = Executors.newFixedThreadPool(this.threadPool);

        List<RedditThread> redditThreads = Collections.synchronizedList(new ArrayList<>());
        this.search(url, redditThreads);

        try {
            this.executorService.awaitTermination(this.timeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IdWallException("Thread can not be finished", e);
        }

        return redditThreads;
    }

    protected void search(String url, List<RedditThread> redditThreads) {

        this.executorService.submit(() -> {

            if (redditThreads.size() >=  this.getMaxThreads()) {
                log.fine("Max reddit thread was reached. Shutdown threads");
                this.executorService.shutdown();
                return;
            }

            try {

                Document document = this.openPage(url);

                Optional<String> nextPage = this.findNextPage(document);
                if (!nextPage.isPresent()) {
                    log.fine("No pages found");
                    this.executorService.shutdown();
                    return;
                }

                this.search(nextPage.get(), redditThreads);

                Elements elements = this.findThreads(document);
                for (Element element : elements) {
                    RedditThread redditThread = this.parseThread(element);

                    if (redditThreads.size() >=  this.getMaxThreads()) {
                        log.fine("Max reddit thread was reached. Shutdown threads");
                        this.executorService.shutdown();
                        return;
                    }

                    if (redditThread.getRate() >= this.getMinRate()) {
                        redditThreads.add(redditThread);
                    }
                }

            } catch (IOException e) {
                throw new IdWallRuntimeException("URL can not be connected: " + url, e);
            } catch (IdWallException e) {
                throw new IdWallRuntimeException(e);
            }

        });

    }

}
