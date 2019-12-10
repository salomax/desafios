package salomax.idwall.crawler.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import salomax.idwall.crawler.exception.IdWallException;
import salomax.idwall.crawler.exception.IdWallRuntimeException;
import salomax.idwall.crawler.model.RedditThread;
import salomax.idwall.crawler.model.SubReddit;
import salomax.idwall.crawler.service.Crawler;
import salomax.idwall.crawler.service.CrawlerService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by marcos.salomao.
 */
@Service
@Log
public class RedditCrawlerService implements CrawlerService {

    @Autowired
    private CrawlerFactory crawlerFactory;
    @Value("${reddit.url}")
    private String redditUrl;

    public List<SubReddit> search(String subReddits, boolean multithread) throws IdWallException {

        String[] subreddits = subReddits.split(";");
        return Stream.of(subreddits)
                .filter(StringUtils::hasText)
                .parallel()
                .map(subReddit -> {
                    try {
                        return this.searchSubreddit(subReddit, multithread);
                    } catch (IdWallException e) {
                        throw new IdWallRuntimeException(e);
                    }
                }).collect(Collectors.toList());

    }

    protected SubReddit searchSubreddit(String subredditName, boolean multithread) throws IdWallException {

        SubReddit subReddit = new SubReddit();
        subReddit.setName(subredditName);

        String page = this.redditUrl + "/r/" + subredditName;

        Crawler<RedditThread> crawler = multithread ? this.crawlerFactory.getMultiThread() : this.crawlerFactory.getSingleThread();
        List<RedditThread> threadList = crawler.search(page);
        subReddit.setThreadList(threadList);

        return subReddit;
    }

}
