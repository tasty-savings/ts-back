package com.example.testysavingsbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TestySavingsBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestySavingsBeApplication.class, args);
    }

}
