package salomax.idwall.crawler.service;

import salomax.idwall.crawler.exception.IdWallException;
import salomax.idwall.crawler.model.SubReddit;

import java.util.List;

/**
 * Created by marcos.salomao.
 */
public interface CrawlerService {

    List<SubReddit> search(String subredditName, boolean multithread) throws IdWallException;

}
