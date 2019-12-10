package salomax.idwall.crawler.exception;

/**
 * Created by marcos.salomao.
 */
public class IdWallException extends Exception {
    public IdWallException(String message) {
        super(message);
    }
    public IdWallException(String message, Exception e) {
        super(message, e);
    }
}
