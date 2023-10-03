package laundry.daeseda.service.mail;

import laundry.daeseda.entity.mail.MailEntity;
import laundry.daeseda.repository.mail.MailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender javaMailSender;
    private final MailRepository mailRepository;

    @Override
    public MimeMessage createMessage(String email, String verificationCode) throws MessagingException, UnsupportedEncodingException {
        System.out.println("보내는 대상 : " + email);
        System.out.println("인증 번호 : " + verificationCode);

        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email);// 보내는 대상
        message.setSubject("daeseda 이메일 인증");// 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += verificationCode + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("zkvn1103@naver.com", "이민욱"));// 보내는 사람

        return message;
    }

    @Override
    public String generateKey() {
        StringBuffer key = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 12; i++) { // 인증코드 12자리
            int index = random.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (random.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (random.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((random.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    @Override
    public String sendMessage(String email) throws Exception {
        MailEntity mailEntity = new MailEntity();
        String verificationCode = generateKey();

        mailEntity.setUserEmail(email);
        mailEntity.setVerificationCode(verificationCode);

        mailRepository.save(mailEntity);

        MimeMessage message = createMessage(email, verificationCode);
        try {
            javaMailSender.send(message);
        } catch (MailException ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException();
        }

        return verificationCode;
    }
}

