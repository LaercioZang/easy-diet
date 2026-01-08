package com.easydiet.backend.controller.v1;

import com.easydiet.backend.config.TestSecurityConfig;
import com.easydiet.backend.config.TestSecurityUtils;
import com.easydiet.backend.controller.v1.auth.UserControllerV1;
import com.easydiet.backend.persistence.user.UserEntity;
import com.easydiet.backend.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerV1.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class UserControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setup() {
        TestSecurityUtils.mockAuthenticatedUser(
                UUID.fromString("11111111-1111-1111-1111-111111111111")
        );
    }

    @Test
    void shouldReturnCurrentUser() throws Exception {

        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        UserEntity user = UserEntity.builder()
                .id(userId)
                .name("Test User")
                .email("test@easydiet.com")
                .createdAt(Instant.now())
                .build();

        when(userService.findById(userId)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.email").value("test@easydiet.com"));
    }
}
