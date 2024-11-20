package com.example.web_project.event.listener;

import com.example.web_project.Entity.User;
import com.example.web_project.Service.user.UserService;
import com.example.web_project.event.RegistrationCompleteEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final UserService userService;
    private  User theUser;
    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        theUser = event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        userService.saveUserVerificationToken(theUser, verificationToken);
        String url = event.getApplicationUrl()+"/api/auth/verifyEmail?token="+verificationToken;
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration : {}",url);
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, "+ theUser.getFirstname() + " " + theUser.getLastname()+", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("bascht2@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    public void sendPasswordResetVerificationEmail(User theUser,String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request Verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, "+ theUser.getFirstname() + " " + theUser.getLastname()+ ", </p>"+
                "<p><b>You recently requested to reset your password,</b>"+"" +
                "Please, follow the link below to complete the action.</p>"+
                "<a href=\"" +url+ "\">Reset password</a>"+
                "<p> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("bascht2@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    public void sendDeliveryManEmail(User user, String password, String baseUrl) {
        String subject = "Welcome to Our Delivery Service!";
        String senderName = "Your Delivery Service Team";
        String mailContent = "<div style='font-family: Arial, sans-serif; font-size: 14px; color: #333;'>"
                + "<p>Hi <strong>" + user.getFirstname() + " " + user.getLastname() + "</strong>,</p>"
                + "<p>Welcome to Our Delivery Service! We're excited to have you on board and look forward to working together.</p>"
                + "<p>Here are your initial login credentials:</p>"
                + "<ul>"
                + "<li>Email: <strong>" + user.getEmail() + "</strong></li>"
                + "<li>Password: <strong>" + password + "</strong> (We strongly recommend you to change your password upon first login)</li>"
                + "</ul>"
                + "<p>Thank you!<br>Your Delivery Service Team</p>"
                + "</div>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("bascht2@gmail.com", senderName);
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to send email to {}: {}", user.getEmail(), e.getMessage());
        }
    }

}
