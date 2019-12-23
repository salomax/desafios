package salomax.idwall.crawler;

import lombok.extern.java.Log;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by marcos.salomao.
 */
@Log
public class RedditParseUnitTest {

    @Test
    public void openConnection() throws IOException {
        Connection connection = Jsoup.connect("https://old.reddit.com");
        assertNotNull(connection);
    }

    @Test
    public void request() throws IOException {
        Connection connection = Jsoup.connect("https://old.reddit.com");
        assertNotNull(connection);

        Connection.Response response = connection.execute();
        assertNotNull(response);
    }

    @Test
    public void parse() throws IOException {

        long time = System.currentTimeMillis();

        Connection connection = Jsoup.connect("https://old.reddit.com");
        assertNotNull(connection);

        System.out.println(System.currentTimeMillis() - time);

        Connection.Response response = connection.execute();
        assertNotNull(response);

        System.out.println(System.currentTimeMillis() - time);

        Document document = response.parse();
        assertNotNull(document);

        System.out.println(System.currentTimeMillis() - time);

    }

}
