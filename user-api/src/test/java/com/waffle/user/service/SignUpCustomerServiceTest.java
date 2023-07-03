package com.waffle.user.service;

import static org.junit.jupiter.api.Assertions.*;

import com.waffle.user.domain.model.Customer;
import com.waffle.user.domain.model.SignUpForm;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService service;

    @Test
    void signUp() {
        SignUpForm form = SignUpForm.builder()
            .name("한")
            .birth(LocalDate.now())
            .email("abc@naver.com")
            .password("1")
            .phone("010-1111")
            .build();

        Customer customer = service.signUp(form);
        assertNotNull(customer.getId());
        assertNotNull(customer.getCreatedAt());
    }
}