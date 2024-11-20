package com.example.web_project.Service.ResetPassword;
import com.example.web_project.Entity.PasswordResetToken;
import com.example.web_project.Entity.User;
import com.example.web_project.Repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;


    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        PasswordResetToken passwordRestToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepository.save(passwordRestToken);
    }

    public String validatePasswordResetToken(String passwordResetToken) {
        PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(passwordResetToken);
        if(passwordToken == null){
            return "Invalid verification token";
        }
        Calendar calendar = Calendar.getInstance();
        if ((passwordToken.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "Link already expired, resend link";
        }
        return "valid";
    }
    public User findUserByPasswordToken(String passwordResetToken) {
        return passwordResetTokenRepository.findByToken(passwordResetToken).getUser();
    }

    public PasswordResetToken findPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }
}