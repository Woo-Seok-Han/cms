package com.waffle.domain.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Aes256UtilTest {
    @Test
    void encrypt() {
        String encrypted = Aes256Util.encrypt("Hello World");
        System.out.println("encrypted = " + encrypted);
        assertEquals(Aes256Util.decrypt(encrypted), "Hello World");
    }


    @Test
    void decrypt() {
    }
}