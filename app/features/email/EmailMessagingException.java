package features.email;

public class EmailMessagingException extends Exception {

    public EmailMessagingException() {}

    public EmailMessagingException(String message) {
        super(message);
    }

    public EmailMessagingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailMessagingException(Throwable cause) {
        super(cause);
    }

    public EmailMessagingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
