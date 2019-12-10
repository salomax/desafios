package salomax.idwall.crawler.model;

import lombok.Data;

import java.util.List;

/**
 * Created by marcos.salomao.
 */
@Data
public class SubReddit {
    private String name;
    private List<RedditThread> threadList;
}
