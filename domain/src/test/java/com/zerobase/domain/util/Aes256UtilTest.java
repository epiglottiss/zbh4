package com.zerobase.domain.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Aes256UtilTest {

    @Test
    void encrypt() {
        String encrypt = Aes256Util.encrypt("encrypt");
        assertEquals(Aes256Util.decrypt(encrypt), "encrypt");
    }

    @Test
    void decrypt() {
    }
}
