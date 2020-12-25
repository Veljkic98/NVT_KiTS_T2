package tim2.CulturalHeritage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.core.env.Environment;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Async
    public void sendVerificationLink(String link, String email) throws MailException, InterruptedException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Registration verification");
        String text = "Please verify your account by clicking the following link : " + link;
        mail.setText(text);
        javaMailSender.send(mail);

    }

    /**
     * Sending notification to all users which are subscribed to CH when
     * new news is created. 
     * 
     * @param email
     * @throws MailException
     * @throws InterruptedException
     */
    @Async
    public void sendNotification(String email, String chName) throws MailException, InterruptedException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Ch notification");
        String text = "You have new news for Cultural Heritage: " + chName;
        mail.setText(text);
        javaMailSender.send(mail);

    }
}
