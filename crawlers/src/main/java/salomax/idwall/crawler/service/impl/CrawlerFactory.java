package salomax.idwall.crawler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import salomax.idwall.crawler.model.RedditThread;
import salomax.idwall.crawler.service.Crawler;

/**
 * Created by marcos.salomao.
 */
@Component
public class CrawlerFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public Crawler<RedditThread> getMultiThread() {
        return BeanFactoryAnnotationUtils.qualifiedBeanOfType(this.applicationContext.getAutowireCapableBeanFactory(),
                Crawler.class, "multiThreadCrawler");
    }

    public Crawler<RedditThread> getSingleThread() {
        return BeanFactoryAnnotationUtils.qualifiedBeanOfType(this.applicationContext.getAutowireCapableBeanFactory(),
                Crawler.class, "singleThreadCrawler");
    }

}
