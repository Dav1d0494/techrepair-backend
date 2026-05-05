package com.techrepair.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class TechrepairBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(TechrepairBackendApplication.class, args);
    }

}
