package com.easydiet.backend.controller.v1;

import com.easydiet.backend.config.TestSecurityConfig;
import com.easydiet.backend.config.TestSecurityUtils;
import com.easydiet.backend.service.diet.DietTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DietTypeControllerV1.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class DietTypeControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DietTypeService dietTypeService;

    @BeforeEach
    void setup() {
        TestSecurityUtils.mockAuthenticatedUser(
                UUID.fromString("11111111-1111-1111-1111-111111111111")
        );
    }

    @Test
    void shouldReturnAllDietTypes() throws Exception {
        when(dietTypeService.findAll(null, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/diet-types"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFilterByActive() throws Exception {
        when(dietTypeService.findAll(true, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/diet-types")
                        .param("active", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFilterBySearch() throws Exception {
        when(dietTypeService.findAll(null, "keto"))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/diet-types")
                        .param("search", "keto"))
                .andExpect(status().isOk());
    }
}
