package group.habooba.backend;

public class DataException extends ApplicationCheckedException {

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}