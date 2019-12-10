package salomax.idwall.crawler.telegram.command.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import salomax.idwall.crawler.exception.IdWallException;
import salomax.idwall.crawler.service.CrawlerService;
import salomax.idwall.crawler.telegram.command.TelegramCommand;
import salomax.idwall.crawler.view.TelegramSubRedditView;

import java.util.function.Consumer;

/**
 * Created by marcos.salomao.
 */
@Component
@Qualifier("/NadaPraFazer")
public class RedditCrawlerTelegramCommand implements TelegramCommand {

    @Autowired
    private CrawlerService crawlerService;
    @Autowired
    private TelegramSubRedditView subRedditView;

    @Override
    public void execute(String[] args, Consumer<String> sendMessage) throws IdWallException {
        this.crawlerService.search(args[0], true)
                .forEach(subReddit -> this.subRedditView.format(subReddit, sendMessage));
    }

}
