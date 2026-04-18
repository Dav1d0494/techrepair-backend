package com.techrepair.backend.config;

import com.techrepair.backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(auth -> auth
                // 🔓 ENDPOINTS PÚBLICOS - NO REQUIEREN TOKEN JWT
                .antMatchers(
                    "/api/auth/**",
                    "/api/noticias-tecnicas/**",
                    "/evidencia-noticias/**",
                    "/api/evidencia-noticias/**",
                    "/api/news/**",
                    "/news-form.jsp",
                    "/registro_evidencia.jsp",
                    "/error",
                    "/favicon.ico",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    // ✅ NUEVOS: RUTAS DE LOS SERVLETS PARA LA EVIDENCIA
                    "/news",
                    "/news/**",
                    "/evidencia-notificacion",
                    "/evidencia-notificacion/**",
                    "/registro-evidencia",
                    "/registro-evidencia/**"
                ).permitAll()
                // 🔒 ENDPOINTS PROTEGIDOS - REQUIEREN TOKEN JWT
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/technician/**").hasAnyRole("TECHNICIAN", "ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic().disable()
            .formLogin().disable();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}