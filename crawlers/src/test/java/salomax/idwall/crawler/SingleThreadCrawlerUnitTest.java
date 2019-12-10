package salomax.idwall.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;
import salomax.idwall.crawler.model.RedditThread;
import salomax.idwall.crawler.service.impl.SingleThreadRedditCrawler;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by marcos.salomao.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class SingleThreadCrawlerUnitTest {

    @Test
    public void whenSearch_thenReturnRedditThreadsRateGreaterThan5k() {

        String url = "some_url";
        String titleMatch = "some_title";
        String nameMatch = "some_name";
        String subredditMatch = "some_subreddit";

        SingleThreadRedditCrawler singleThreadRedditCrawler = new SingleThreadRedditCrawler();
        ReflectionTestUtils.setField(singleThreadRedditCrawler, "maxThreads", 50);
        ReflectionTestUtils.setField(singleThreadRedditCrawler, "minRate", 5000);

        PowerMockito.mockStatic(Jsoup.class);
        Connection connection = mock(Connection.class);
        Document document = mock(Document.class);
        Elements threads = new Elements();
        Element element = mock(Element.class);
        threads.add(element);
        Elements title = mock(Elements.class);
        Elements threadLink = mock(Elements.class);
        Elements rate = mock(Elements.class);

        try {

            when(connection.userAgent(anyString())).thenReturn(connection);
            when(connection.get()).thenReturn(document);
            when(document.select(eq("div.thing"))).thenReturn(threads);

            when(element.select(eq("a.title"))).thenReturn(title);
            when(element.attr(eq("data-fullname"))).thenReturn(nameMatch);
            when(element.attr(eq("data-subreddit-prefixed"))).thenReturn(subredditMatch);
            when(element.select(eq("a.comments"))).thenReturn(mock(Elements.class));
            when(threadLink.size()).thenReturn(0);
            when(element.select(eq("a.title.may-blank[target=\"_blank\"]"))).thenReturn(threadLink);
            when(rate.attr(eq("title"))).thenReturn("5000");
            when(element.select(eq("div.score.unvoted"))).thenReturn(rate);
            when(title.text()).thenReturn(titleMatch);

            PowerMockito.when(Jsoup.connect(eq(url))).thenReturn(connection);
            List<RedditThread> redditThreadList = singleThreadRedditCrawler.search(url);

            assertNotNull(redditThreadList);
            assertTrue(redditThreadList.size() == 1);

            RedditThread redditThread = redditThreadList.get(0);
            assertNotNull(redditThread);
            assertThat(redditThread.getName(), is(nameMatch));
            assertThat(redditThread.getTitle(), is(titleMatch));
            assertThat(redditThread.getSubReddit(), is(subredditMatch));
            assertThat(redditThread.getRate(), is(5000));

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    public void whenSearch_thenReturnRedditThreadsRateLessThan5k() {

        String url = "some_url";
        String titleMatch = "some_title";
        String nameMatch = "some_name";
        String subredditMatch = "some_subreddit";

        SingleThreadRedditCrawler singleThreadRedditCrawler = new SingleThreadRedditCrawler();
        ReflectionTestUtils.setField(singleThreadRedditCrawler, "maxThreads", 50);
        ReflectionTestUtils.setField(singleThreadRedditCrawler, "minRate", 5000);

        PowerMockito.mockStatic(Jsoup.class);
        Connection connection = mock(Connection.class);
        Document document = mock(Document.class);
        Elements threads = new Elements();
        Element element = mock(Element.class);
        threads.add(element);
        Elements title = mock(Elements.class);
        Elements threadLink = mock(Elements.class);
        Elements rate = mock(Elements.class);

        try {

            when(connection.userAgent(anyString())).thenReturn(connection);
            when(connection.get()).thenReturn(document);
            when(document.select(eq("div.thing"))).thenReturn(threads);

            when(element.select(eq("a.title"))).thenReturn(title);
            when(element.attr(eq("data-fullname"))).thenReturn(nameMatch);
            when(element.attr(eq("data-subreddit-prefixed"))).thenReturn(subredditMatch);
            when(element.select(eq("a.comments"))).thenReturn(mock(Elements.class));
            when(threadLink.size()).thenReturn(0);
            when(element.select(eq("a.title.may-blank[target=\"_blank\"]"))).thenReturn(threadLink);
            when(rate.attr(eq("title"))).thenReturn("4999");
            when(element.select(eq("div.score.unvoted"))).thenReturn(rate);
            when(title.text()).thenReturn(titleMatch);

            PowerMockito.when(Jsoup.connect(eq(url))).thenReturn(connection);
            List<RedditThread> redditThreadList = singleThreadRedditCrawler.search(url);

            assertNotNull(redditThreadList);
            assertTrue(redditThreadList.size() == 0);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

}
