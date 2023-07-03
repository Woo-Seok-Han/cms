package com.waffle.user.service;

import com.waffle.user.client.mailgun.MailgunClient;
import com.waffle.user.client.mailgun.SendMailForm;
import lombok.RequiredArgsConstructor;
import feign.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailgunClient mailgunClient;

    public String sendMail() {

        SendMailForm form = SendMailForm.builder()
            .from("woosuk1893@naver.com")
            .to("woosuk1893@naver.com")
            .subject("test mail")
            .text("hello mail")
            .build();

        return mailgunClient.sendEmail(form);
    }
}
