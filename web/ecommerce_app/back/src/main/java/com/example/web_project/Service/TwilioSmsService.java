package com.example.web_project.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String fromPhoneNumber;

    public void sendSms(String to, String messageBody) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(fromPhoneNumber),
                messageBody
        ).create();
        System.out.println("SMS sent: " + message.getSid());
    }
}