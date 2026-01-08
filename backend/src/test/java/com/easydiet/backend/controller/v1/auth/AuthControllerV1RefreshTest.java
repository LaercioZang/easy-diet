package com.easydiet.backend.controller.v1.auth;

import com.easydiet.backend.config.JwtProperties;
import com.easydiet.backend.config.JwtTokenProvider;
import com.easydiet.backend.persistence.auth.RefreshTokenEntity;
import com.easydiet.backend.persistence.user.UserEntity;
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

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthControllerV1.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerV1RefreshTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtProperties jwtProperties;

    @MockBean
    private AuthService authService;

    @Test
    void shouldRefreshTokenSuccessfully() throws Exception {

        UserEntity user = UserEntity.builder()
                .id(UUID.randomUUID())
                .email("test@easydiet.com")
                .build();

        when(refreshTokenService.validateAndRotate("refresh-token"))
                .thenReturn(user);

        when(jwtTokenProvider.generateToken(user))
                .thenReturn("new-access-token");

        when(jwtProperties.getExpirationSeconds())
                .thenReturn(3600L);

        RefreshTokenEntity newRefresh =
                RefreshTokenEntity.builder()
                        .token("new-refresh-token")
                        .build();

        when(refreshTokenService.issue(user))
                .thenReturn(newRefresh);

        mockMvc.perform(
                post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            { "refreshToken": "refresh-token" }
                        """)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").value("new-access-token"))
        .andExpect(jsonPath("$.expiresIn").value(3600))
        .andExpect(jsonPath("$.refreshToken").value("new-refresh-token"));
    }
}
