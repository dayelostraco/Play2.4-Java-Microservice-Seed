package features.email;

import java.io.File;

/**
 * Created by Dayel Ostraco
 * 10/3/15.
 */
public interface EmailService {

    void sendEmail(String from, String toEmail, String ccEmail, String bccEmail,
                   String subject, String body, boolean asHtml)
            throws EmailMessagingException;

    void sendEmail(String from, String toEmail, String ccEmail, String bccEmail,
                   String subject, String body, File file, boolean asHtml)
            throws EmailMessagingException;


    void sendEmail(String from, String[] toEmails, String[] ccEmails, String[] bccEmails,
                   String subject, String body, boolean asHtml)
            throws EmailMessagingException;

    void sendEmail(String from, String[] toEmails, String[] ccEmails, String[] bccEmails,
                   String subject, String body, File file, boolean asHtml)
            throws EmailMessagingException;
}
