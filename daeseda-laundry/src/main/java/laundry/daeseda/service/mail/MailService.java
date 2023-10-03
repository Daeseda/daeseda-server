package laundry.daeseda.service.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

public interface MailService {

    MimeMessage createMessage(String message, String verificationCode) throws MessagingException, UnsupportedEncodingException;
    String generateKey();
    String sendMessage(String email) throws Exception;
}
