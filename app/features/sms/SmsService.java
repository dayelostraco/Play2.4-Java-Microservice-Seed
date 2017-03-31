package features.sms;

import java.util.List;

/**
 * Created by Dayel Ostraco
 * 10/3/15.
 */
public interface SmsService {

    void sendSmsMessage(String phoneNumber, String messageBody)
            throws SmsMessagingException;

    void sendMmsMessage(String phoneNumber, String messageBody, String mediaUrl)
            throws SmsMessagingException;

    void sendMmsMessage(String phoneNumber, String messageBody, List<String> mediaUrls)
            throws SmsMessagingException;
}
