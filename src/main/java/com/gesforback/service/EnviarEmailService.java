package com.gesforback.service;

import com.gesforback.email.EmailService;
import com.gesforback.email.Mail;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sud
 */
@Service
public class EnviarEmailService {

    @Autowired
    private EmailService emailService;

    public void enviarEmail() {

        try {
            Mail mail = new Mail();
            mail.setFrom("sudtecnologia@gmail.com");
            mail.setMailTo("brunohallef@gmail.com");
            mail.setSubject("Email with Spring boot and thymeleaf template!");
            Map<String, Object> model = new HashMap<>();
            model.put("name", "Developer!");
            model.put("location", "United States");
            model.put("sign", "Java Developer");
            mail.setProps(model);
            emailService.sendEmail(mail);
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(EnviarEmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
