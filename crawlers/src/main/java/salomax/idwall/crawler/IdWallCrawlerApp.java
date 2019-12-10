package salomax.idwall.crawler;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * Created by marcos.salomao.
 */
@SpringBootApplication
@Log
public class IdWallCrawlerApp {


    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(IdWallCrawlerApp.class, args).close();
        System.exit(0);
    }

}
