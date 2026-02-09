package com.techrepair.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TechrepairBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(TechrepairBackendApplication.class, args);
    }
}
