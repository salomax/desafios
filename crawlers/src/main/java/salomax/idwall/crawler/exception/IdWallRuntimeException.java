package salomax.idwall.crawler.exception;

/**
 * Created by marcos.salomao.
 */
public class IdWallRuntimeException extends RuntimeException {
    public IdWallRuntimeException(Exception e) {
        super(e);
    }
    public IdWallRuntimeException(String message, Exception e) {
        super(message, e);
    }
}
