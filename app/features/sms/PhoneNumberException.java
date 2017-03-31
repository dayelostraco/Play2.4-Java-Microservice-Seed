package features.sms;

public class PhoneNumberException extends Exception {

    public PhoneNumberException() {}

    public PhoneNumberException(String message) {
        super(message);
    }

    public PhoneNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneNumberException(Throwable cause) {
        super(cause);
    }

    public PhoneNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
