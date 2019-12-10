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
import salomax.idwall.crawler.model.RedditThread;
import salomax.idwall.crawler.service.Crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by marcos.salomao.
 */
@Component
@Qualifier("singleThreadCrawler")
@Log
public class SingleThreadRedditCrawler extends AbstractRedditCrawler implements Crawler<RedditThread> {

    @Override
    public List<RedditThread> search(String url) throws IdWallException {
        List<RedditThread> redditThreads = new ArrayList<>();
        this.search(url, redditThreads);
        return redditThreads;
    }

    protected void search(String url, List<RedditThread> redditThreads) throws IdWallException {

        try {

            Document document = this.openPage(url);

            Elements elements = this.findThreads(document);
            for (Element element : elements) {
                RedditThread redditThread = this.parseThread(element);

                if (redditThread.getRate() >= this.getMinRate()) {
                    redditThreads.add(redditThread);

                    if (redditThreads.size() >=  this.getMaxThreads()) {
                        return;
                    }
                }

            }

            Optional<String> nextPage = this.findNextPage(document);
            if (nextPage.isPresent()) {
                this.search(nextPage.get(), redditThreads);
            }

        } catch (IOException e) {
            throw new IdWallException("URL can not be connected: " + url, e);
        }

    }

}
