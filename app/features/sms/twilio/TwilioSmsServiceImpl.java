package features.sms.twilio;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import features.sms.PhoneNumberException;
import features.sms.PhoneNumberValidator;
import features.sms.SmsMessagingException;
import features.sms.SmsService;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import play.Configuration;
import play.Logger;
import play.Play;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dayel Ostraco
 * 9/28/15.
 */
@SuppressWarnings("unused")
public class TwilioSmsServiceImpl implements SmsService {

    private static final Logger.ALogger LOGGER = Logger.of(TwilioSmsServiceImpl.class);

    private static final String SMS_PHONE_NUMBER = "twilio.sms.phoneNumber";
    private static final String MMS_PHONE_NUMBER = "twilio.mms.phoneNumber";
    private static final String VOICE_PHONE_NUMBER = "twilio.voice.phoneNumber";
    private static final String ACCOUNT_SID = "twilio.accountSid";
    private static final String PRIMARY_TOKEN = "twilio.primaryToken";
    private static final String SECONDARY_TOKEN = "twilio.secondaryToken";
    private static final String TEST_ACCOUNT_SID = "twilio.test.accountSid";
    private static final String TEST_ACCOUNT_TOKEN = "twilio.test.token";

    private String smsPhoneNumber;
    private String mmsPhoneNumber;
    private String voicePhoneNumber;
    private String accountSid;
    private String primaryToken;
    private String secondaryToken;
    private String testAccountSid;
    private String testAccountToken;
    private TwilioRestClient client;

    public TwilioSmsServiceImpl() {
        Configuration config = Play.application().configuration();

        smsPhoneNumber = config.getString(SMS_PHONE_NUMBER);
        mmsPhoneNumber = config.getString(MMS_PHONE_NUMBER);
        voicePhoneNumber = config.getString(VOICE_PHONE_NUMBER);
        accountSid = config.getString(ACCOUNT_SID);
        primaryToken = config.getString(PRIMARY_TOKEN);
        secondaryToken = config.getString(SECONDARY_TOKEN);
        testAccountSid = config.getString(TEST_ACCOUNT_SID);
        testAccountToken = config.getString(TEST_ACCOUNT_TOKEN);
        client = new TwilioRestClient(accountSid, primaryToken);
    }

    /**
     * Sends a SMS message to the provided phoneNumber via Twilio.
     *
     * @param phoneNumber Phone number String with no special characters
     * @param messageBody SMS message String between 1-140 characters
     */
    public void sendSmsMessage(String phoneNumber, String messageBody)
            throws SmsMessagingException {

        if (messageBody.length() > 140 || messageBody.length() == 0) {
            throw new SmsMessagingException(
                    new IllegalArgumentException("The provided messageBody needs to be between 1-140 characters."));
        }

        try {
            // Build the parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("To",
                    PhoneNumberValidator.convertToMinimizedPhoneNumber(phoneNumber,
                            PhoneNumberValidator.PhoneNumberRegion.NORTH_AMERICA)));
            params.add(new BasicNameValuePair("From", smsPhoneNumber));
            params.add(new BasicNameValuePair("Body", messageBody));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();

            Message message = messageFactory.create(params);

        } catch (TwilioRestException e) {
            LOGGER.error("Could not send SMS Text Message to " + phoneNumber, e);
            throw new SmsMessagingException("Could not send SMS Text Message to " + phoneNumber);
        } catch (PhoneNumberException e) {
            LOGGER.error("Could not send SMS Text Message to " + phoneNumber, e);
            throw new SmsMessagingException(e.getMessage(), e);
        }
    }

    /**
     * Sends a MMS message with Media URLs to the provided phoneNumber via Twilio.
     *
     * @param phoneNumber Phone number String with no special characters
     * @param messageBody SMS message String between 1-140 characters
     * @param mediaUrl    String URL of the media you want included in the MMS
     */
    public void sendMmsMessage(String phoneNumber, String messageBody, String mediaUrl)
            throws SmsMessagingException {

        List<String> mediaUrls = new ArrayList<>();
        mediaUrls.add(mediaUrl);

        sendMmsMessage(phoneNumber, messageBody, mediaUrls);
    }

    /**
     * Sends a MMS message with Media URLs to the provided phoneNumber via Twilio.
     *
     * @param phoneNumber Phone number String with no special characters
     * @param messageBody SMS message String between 1-140 characters
     * @param mediaUrls   Collection of up to 10 mediaUrls to be included in the MMS
     */
    public void sendMmsMessage(String phoneNumber, String messageBody, List<String> mediaUrls)
            throws SmsMessagingException {

        if (messageBody.length() > 140 || messageBody.length() == 0) {
            throw new SmsMessagingException(
                    new IllegalArgumentException("The provided messageBody needs to be between 1-140 characters."));
        }

        if(mediaUrls.size()>10) {
            throw new SmsMessagingException("You can only have 1-10 media URLs included on a MMS message");
        }

        try {
            // Build the parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("To",
                    PhoneNumberValidator.convertToMinimizedPhoneNumber(phoneNumber,
                            PhoneNumberValidator.PhoneNumberRegion.NORTH_AMERICA)));
            params.add(new BasicNameValuePair("From", mmsPhoneNumber));
            params.add(new BasicNameValuePair("Body", messageBody));
            mediaUrls.forEach((mediaUrl) -> params.add(new BasicNameValuePair("MediaUrl", mediaUrl)));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();

            Message message = messageFactory.create(params);

        } catch (TwilioRestException e) {
            LOGGER.error("Could not send MMS Text Message to " + phoneNumber, e);
            throw new SmsMessagingException("Could not send MMS Text Message to " + phoneNumber);
        } catch (PhoneNumberException e) {
            LOGGER.error("Could not send MMS Text Message to " + phoneNumber, e);
            throw new SmsMessagingException(e.getMessage(), e);
        }
    }
}
