package salomax.idwall.crawler.model;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * Created by marcos.salomao.
 */
@Data
public class RedditThread {

    private String name;
    private String title;
    private Integer rate;
    private String subReddit;
    private String commentUrl;
    private String threadUrl;
    private OffsetDateTime timestamp;

}
