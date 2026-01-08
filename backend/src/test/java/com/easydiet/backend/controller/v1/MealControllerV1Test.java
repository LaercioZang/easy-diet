package com.easydiet.backend.controller.v1;

import com.easydiet.backend.config.TestSecurityConfig;
import com.easydiet.backend.config.TestSecurityUtils;
import com.easydiet.backend.controller.v1.meal.MealControllerV1;
import com.easydiet.backend.persistence.meal.MealEntity;
import com.easydiet.backend.service.meal.MealService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MealControllerV1.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class MealControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MealService mealService;

    @BeforeEach
    void setup() {
        TestSecurityUtils.mockAuthenticatedUser(
                UUID.fromString("11111111-1111-1111-1111-111111111111")
        );
    }

    @Test
    void shouldCreateMeal() throws Exception {

        MealEntity meal = MealEntity.builder()
                .id(UUID.randomUUID())
                .dayOfWeek(DayOfWeek.MONDAY)
                .name("Breakfast")
                .mealOrder(1)
                .totalCalories(BigDecimal.valueOf(500))
                .totalProtein(BigDecimal.TEN)
                .totalCarbs(BigDecimal.TEN)
                .totalFat(BigDecimal.TEN)
                .foodItems(new ArrayList<>())
                .build();

        when(mealService.create(any(), any()))
                .thenReturn(meal);

        mockMvc.perform(
                post("/api/v1/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "dayOfWeek": "MONDAY",
                              "name": "Breakfast",
                              "mealOrder": 1,
                              "calorieTarget": 500,
                              "proteinTarget": 10,
                              "carbsTarget": 10,
                              "fatTarget": 10
                            }
                        """)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Breakfast"));
    }

    @Test
    void shouldListMealsByDay() throws Exception {

        MealEntity meal = MealEntity.builder()
                .id(UUID.randomUUID())
                .dayOfWeek(DayOfWeek.MONDAY)
                .name("Lunch")
                .mealOrder(2)
                .totalCalories(BigDecimal.valueOf(700))
                .totalProtein(BigDecimal.TEN)
                .totalCarbs(BigDecimal.TEN)
                .totalFat(BigDecimal.TEN)
                .foodItems(new ArrayList<>())
                .build();

        when(mealService.findByDay(any(), any()))
                .thenReturn(List.of(meal));

        mockMvc.perform(get("/api/v1/meals?day=MONDAY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Lunch"));
    }

    @Test
    void shouldDeleteMeal() throws Exception {

        mockMvc.perform(delete("/api/v1/meals/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }
}
