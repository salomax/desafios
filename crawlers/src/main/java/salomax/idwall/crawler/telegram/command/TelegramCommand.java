package salomax.idwall.crawler.telegram.command;

import salomax.idwall.crawler.exception.IdWallException;

import java.util.function.Consumer;

/**
 * Created by marcos.salomao.
 */
public interface TelegramCommand {

    void execute(String[] args, Consumer<String> sendMessage) throws IdWallException;

}
