package com.easydiet.backend.controller.v1;

import com.easydiet.backend.config.TestSecurityConfig;
import com.easydiet.backend.config.TestSecurityUtils;
import com.easydiet.backend.service.FoodCategoryService;
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

@WebMvcTest(FoodCategoryControllerV1.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class FoodCategoryControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodCategoryService foodCategoryService;

    @BeforeEach
    void setup() {
        TestSecurityUtils.mockAuthenticatedUser(
                UUID.fromString("11111111-1111-1111-1111-111111111111")
        );
    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        when(foodCategoryService.findAll(null, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/food-categories"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFilterByActive() throws Exception {
        when(foodCategoryService.findAll(true, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/food-categories")
                        .param("active", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFilterBySearch() throws Exception {
        when(foodCategoryService.findAll(null, "prot"))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/food-categories")
                        .param("search", "prot"))
                .andExpect(status().isOk());
    }
}
