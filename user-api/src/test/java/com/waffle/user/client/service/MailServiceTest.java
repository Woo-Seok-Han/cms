package com.waffle.user.client.service;

import com.waffle.user.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void EmailTest() {
        String response = mailService.sendMail();
        System.out.println("response = " + response);
    }
}