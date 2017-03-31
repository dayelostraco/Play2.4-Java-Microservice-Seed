package features.sms;

public class SmsMessagingException extends Exception {

    public SmsMessagingException() {}

    public SmsMessagingException(String message) {
        super(message);
    }

    public SmsMessagingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsMessagingException(Throwable cause) {
        super(cause);
    }

    public SmsMessagingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
