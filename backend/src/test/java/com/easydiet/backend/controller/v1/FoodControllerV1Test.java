package com.easydiet.backend.controller.v1;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.domain.food.enums.Category;
import com.easydiet.backend.service.FoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodControllerV1.class)
@ActiveProfiles("test")
class FoodControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodService foodService;

    @Test
    void shouldReturnFoodsWithoutFilters() throws Exception {
        when(foodService.findAll(null, null, null))
                .thenReturn(
                        List.of(
                                Food.builder()
                                        .id(UUID.randomUUID())
                                        .name("Chicken")
                                        .calories(120)
                                        .protein(BigDecimal.TEN)
                                        .carbs(BigDecimal.ZERO)
                                        .fat(BigDecimal.ONE)
                                        .build()
                        )
                );

        mockMvc.perform(get("/api/v1/foods"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFilterByCategory() throws Exception {
        when(foodService.findAll(null, null, Category.PROTEIN))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/foods")
                        .param("category", "PROTEIN"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFilterByActive() throws Exception {
        when(foodService.findAll(true, null, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/foods")
                        .param("active", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFilterBySearch() throws Exception {
        when(foodService.findAll(null, "chicken", null))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/foods")
                        .param("search", "chicken"))
                .andExpect(status().isOk());
    }
}
