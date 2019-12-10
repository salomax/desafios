package salomax.idwall.crawler.service.impl;

import lombok.AccessLevel;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import salomax.idwall.crawler.exception.IdWallException;
import salomax.idwall.crawler.model.RedditThread;
import salomax.idwall.crawler.service.Crawler;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by marcos.salomao.
 */
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractRedditCrawler implements Crawler<RedditThread> {

    @Value("${reddit.max-threads:50}")
    private Integer maxThreads;
    @Value("${reddit.min-rate:5000}")
    private Integer minRate;

    abstract void search(String url, List<RedditThread> redditThreads) throws IdWallException;

    protected Document openPage(String url) throws IOException {
        return Jsoup.connect(url).userAgent("bot").get();
    }

    protected Optional<String> findNextPage(Document document) throws IdWallException {
        Element nextPage = document.selectFirst("span.next-button a");
        if (nextPage != null && StringUtils.hasText(nextPage.attr("href"))) {
            return Optional.of(nextPage.attr("href"));
        }
        return Optional.empty();
    }

    protected RedditThread parseThread(Element element) {

        RedditThread redditThread = new RedditThread();

        redditThread.setName(element.attr("data-fullname"));
        redditThread.setTitle(element.select("a.title").text());
        redditThread.setSubReddit(element.attr("data-subreddit-prefixed"));
        redditThread.setCommentUrl(element.select("a.comments").attr("href"));
        redditThread.setTimestamp(OffsetDateTime.now());

        Elements elements = element.select("a.title.may-blank[target=\"_blank\"]");
        if (elements.size() == 1) {
            redditThread.setThreadUrl(elements.attr("href"));
        } else {
            redditThread.setThreadUrl(element.select("a.comments").attr("href"));
        }

        try {
            redditThread.setRate(Integer.valueOf(element.select("div.score.unvoted").attr("title")));
        } catch (NumberFormatException e) {
            redditThread.setRate(0);
        }

        return redditThread;
    }

    protected Elements findThreads(Document document) {
        return document.select("div.thing");
    }

}
