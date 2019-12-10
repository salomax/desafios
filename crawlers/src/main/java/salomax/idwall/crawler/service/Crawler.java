package salomax.idwall.crawler.service;

import salomax.idwall.crawler.exception.IdWallException;

import java.util.List;

/**
 * Created by marcos.salomao.
 */
public interface Crawler<T> {

    List<T> search(String url) throws IdWallException;

}
