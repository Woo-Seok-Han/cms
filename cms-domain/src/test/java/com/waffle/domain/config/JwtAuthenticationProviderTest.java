package com.waffle.domain.config;

import com.waffle.domain.common.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtAuthenticationProviderTest {

    @Test
    @DisplayName("토큰 검증")
    void validateToken() {
        final JwtAuthenticationProvider provider = new JwtAuthenticationProvider();

        String email = "woosuk1893@naver.com";
        Long id = 1L;

        String token = provider.createToken(email, id, UserType.CUSTOMER);
        boolean validated = provider.validateToken(token);

        Assertions.assertEquals(validated, true);
    }

    @Test
    void getUserVo() {
    }
}