package com.techrepair.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
    // Esta clase separa la auditoría del contexto global para no interferir con @WebMvcTest
}
