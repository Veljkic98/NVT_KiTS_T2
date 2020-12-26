package tim2.CulturalHeritage.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import tim2.CulturalHeritage.restTemplateHelp.SmtpServerRule;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static tim2.CulturalHeritage.constants.EmailConstants.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

    @Test
    public void testSendVerificationLink() throws MessagingException, IOException {

        emailService.sendVerificationLink(LINK_BASE_URL + USER_ID, USER_EMAIL);

        MimeMessage[] receivedMessages = smtpServerRule.getMessages();

        assertEquals(1, receivedMessages.length);

        MimeMessage current = receivedMessages[0];

        assertEquals(SUBJECT, current.getSubject());
        assertEquals(USER_EMAIL, current.getAllRecipients()[0].toString());
        assertTrue(String.valueOf(current.getContent()).contains(VERIFY_TEXT));
    }
}
