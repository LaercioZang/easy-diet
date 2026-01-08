package com.easydiet.backend.controller;

import com.easydiet.backend.config.TestSecurityConfig;
import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.domain.food.FoodCategory;
import com.easydiet.backend.domain.food.enums.Category;
import com.easydiet.backend.service.food.FoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoodController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodService foodService;

    @Test
    void shouldReturnAllActiveFoods() throws Exception {
        Food food = buildFood();

        when(foodService.findAllActive())
            .thenReturn(List.of(food));

        mockMvc.perform(get("/api/foods"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(food.getId().toString()))
            .andExpect(jsonPath("$[0].name").value(food.getName()))
            .andExpect(jsonPath("$[0].calories").value(food.getCalories()))
            .andExpect(jsonPath("$[0].protein").value(food.getProtein().doubleValue()))
            .andExpect(jsonPath("$[0].category.code").value("PROTEIN"));
    }

    @Test
    void shouldReturnFoodsByCategory() throws Exception {
        Food food = buildFood();

        when(foodService.findActiveByCategory(Category.PROTEIN))
            .thenReturn(List.of(food));

        mockMvc.perform(get("/api/foods/category/PROTEIN"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].category.code").value("PROTEIN"))
            .andExpect(jsonPath("$[0].name").value(food.getName()));
    }

    // ---------- helpers ----------

    private Food buildFood() {
        return Food.builder()
            .id(UUID.randomUUID())
            .name("Chicken Breast")
            .calories(BigDecimal.valueOf(165))
            .protein(BigDecimal.valueOf(31))
            .carbs(BigDecimal.ZERO)
            .fat(BigDecimal.valueOf(3.6))
            .category(
                FoodCategory.builder()
                    .id(UUID.randomUUID())
                    .code(Category.PROTEIN)
                    .name("Protein")
                    .build()
            )
            .build();
    }
}
