package com.techrepair.backend.controller; // Verifica que el paquete coincida con la carpeta

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techrepair.backend.dto.request.LoginRequest;
import com.techrepair.backend.dto.response.ApiResponse;
import com.techrepair.backend.service.AuthService;
import com.techrepair.backend.security.JwtTokenProvider;
import com.techrepair.backend.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void login_returnsOk() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@example.com");
        req.setPassword("password");
        
        when(authService.login(any())).thenReturn(new ApiResponse<>(true, "ok", null));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }
}