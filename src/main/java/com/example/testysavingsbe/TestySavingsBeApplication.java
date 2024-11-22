package com.example.testysavingsbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.testysavingsbe.domain.recipe.repository")
public class TestySavingsBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestySavingsBeApplication.class, args);
    }

}
