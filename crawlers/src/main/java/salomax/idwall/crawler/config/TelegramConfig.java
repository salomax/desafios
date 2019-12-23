package salomax.idwall.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import salomax.idwall.crawler.exception.IdWallRuntimeException;
import salomax.idwall.crawler.telegram.SalomaxIdWallBot;

/**
 * Created by marcos.salomao.
 */
@Configuration
@Import({
    SalomaxIdWallBot.class
})
public class TelegramConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(LongPollingBot salomaxIdWallBot) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
//        try {
//            telegramBotsApi.registerBot(salomaxIdWallBot);
//        } catch (TelegramApiRequestException e) {
//            throw new IdWallRuntimeException("Telegram API not setup", e);
//        }
        return telegramBotsApi;
    }

}
