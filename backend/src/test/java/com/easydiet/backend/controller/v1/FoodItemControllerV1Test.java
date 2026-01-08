package com.easydiet.backend.controller.v1;

import com.easydiet.backend.config.TestSecurityConfig;
import com.easydiet.backend.config.TestSecurityUtils;
import com.easydiet.backend.persistence.food.FoodEntity;
import com.easydiet.backend.persistence.meal.FoodItemEntity;
import com.easydiet.backend.service.food.FoodItemService;
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
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoodItemControllerV1.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class FoodItemControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FoodItemService foodItemService;

    @BeforeEach
    void setup() {
        TestSecurityUtils.mockAuthenticatedUser(
                UUID.fromString("11111111-1111-1111-1111-111111111111")
        );
    }

    @Test
    void shouldCreateFoodItem() throws Exception {

        FoodEntity food = FoodEntity.builder()
                .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .name("Chicken Breast")
                .build();

        FoodItemEntity item = FoodItemEntity.builder()
                .id(UUID.randomUUID())
                .food(food)
                .quantity(BigDecimal.valueOf(100))
                .calories(BigDecimal.valueOf(120))
                .protein(BigDecimal.valueOf(22))
                .carbs(BigDecimal.ZERO)
                .fat(BigDecimal.valueOf(2))
                .build();

        when(foodItemService.create( any(UUID.class),
                any(UUID.class),
                any(UUID.class),
                anyDouble()))
                .thenReturn(item);

        mockMvc.perform(
                post("/api/v1/meals/{mealId}/food-items", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "foodId": "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
                              "quantity": 100
                            }
                        """)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.calories").value(120));
    }

    @Test
    void shouldListFoodItemsByMeal() throws Exception {
        FoodEntity food = FoodEntity.builder()
                .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .name("Chicken Breast")
                .build();


        FoodItemEntity item = FoodItemEntity.builder()
                .id(UUID.randomUUID())
                .food(food)
                .quantity(BigDecimal.valueOf(100))
                .calories(BigDecimal.valueOf(120))
                .protein(BigDecimal.valueOf(22))
                .carbs(BigDecimal.ZERO)
                .fat(BigDecimal.valueOf(2))
                .build();

        when(foodItemService.findByMeal(any(), any()))
                .thenReturn(List.of(item));

        mockMvc.perform(
                get("/api/v1/meals/{mealId}/food-items", UUID.randomUUID())
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].calories").value(120));
    }

    @Test
    void shouldDeleteFoodItem() throws Exception {

        mockMvc.perform(
                delete("/api/v1/food-items/{id}", UUID.randomUUID())
        )
        .andExpect(status().isNoContent());
    }
}
