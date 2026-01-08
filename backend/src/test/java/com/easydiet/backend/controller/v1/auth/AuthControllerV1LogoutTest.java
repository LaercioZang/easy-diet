package com.easydiet.backend.controller.v1.auth;

import com.easydiet.backend.config.JwtProperties;
import com.easydiet.backend.config.JwtTokenProvider;
import com.easydiet.backend.service.auth.AuthService;
import com.easydiet.backend.service.auth.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthControllerV1.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerV1LogoutTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtProperties jwtProperties;

    @Test
    void shouldLogoutSuccessfully() throws Exception {

        mockMvc.perform(
                post("/api/v1/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            { "refreshToken": "refresh-token" }
                        """)
        )
        .andExpect(status().isNoContent());
    }
}
