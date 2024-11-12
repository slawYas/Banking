package com.kata.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, "--spring.profiles.active=test");
    }

}
