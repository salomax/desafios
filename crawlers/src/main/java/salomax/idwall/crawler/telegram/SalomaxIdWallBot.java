package salomax.idwall.crawler.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import salomax.idwall.crawler.exception.IdWallException;
import salomax.idwall.crawler.exception.IdWallRuntimeException;
import salomax.idwall.crawler.telegram.command.TelegramCommand;

import java.util.Arrays;


/**
 * Created by marcos.salomao.
 */
@Component
public class SalomaxIdWallBot extends TelegramLongPollingBot {

    @Autowired
    private ApplicationContext applicationContext;
    @Value("${telegram.bot.token}")
    private String token;
    @Value("${telegram.bot.username}")
    private String username;

    @Override
    public String getBotToken() {
        return this.token;
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public void onUpdateReceived(Update update) {

        try {

            String messageText = this.getMessageText(update);
            TelegramCommand command = this.findCommand(messageText);
            String[] args = this.findArgs(messageText);

            this.sendMessage(update.getMessage().getChatId(),
                    "Ok! I got your request. As soon as it's done you'll receive a reply.");

            command.execute(args, (message) -> this.sendMessage(update.getMessage().getChatId(), message));

        } catch (Exception e) {
            this.sendMessage(update.getMessage().getChatId(), e.getMessage());
        }

    }

    private void sendMessage(Long chatId, String message) {
        try {
            this.execute(new SendMessage().setChatId(chatId).setText(message));
        } catch (TelegramApiException e) {
            throw new IdWallRuntimeException("Message not sent", e);
        }
    }

    private TelegramCommand findCommand(String message) throws IdWallException {

        String command = message.substring(0,
                (message.indexOf(' ') > 0 ? message.indexOf(' ') : message.length()));

        try {

            return BeanFactoryAnnotationUtils.qualifiedBeanOfType(
                    this.applicationContext.getAutowireCapableBeanFactory(),
                    TelegramCommand.class, command);

        } catch (Exception e) {
            throw new IdWallException(String.format(
                    "I don't know what '%s' means. Did you write it correctly?", message));
        }

    }

    private String[] findArgs(String message) {
        String[] args = message.split(" ");
        if (args.length > 1) {
            return Arrays.copyOfRange(args, 1, args.length);
        }
        return null;
    }

    private String getMessageText(Update update) throws IdWallException {
        try {
            return update.getMessage().getText();
        } catch (Exception e) {
            throw new IdWallException("Something is wrong! No message text found. Send it again please?");
        }
    }

}
