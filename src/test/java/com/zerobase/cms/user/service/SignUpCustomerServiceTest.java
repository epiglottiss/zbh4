package com.zerobase.cms.user.service;

import com.zerobase.cms.user.domain.SignUpForm;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignUpCustomerServiceTest {

    @Autowired
    private  SignUpCustomerService service;

    @Test
    void signUp() {
        SignUpForm form = SignUpForm.builder()
                .name("name")
                .birth(LocalDate.now())
                .email("test@test123.com")
                .password("1")
                .phone("010101010")
                .build();
        assertNotNull(service.signUp(form).getEmail());

    }
}
