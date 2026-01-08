package com.easydiet.backend.controller.v1.diet;

import com.easydiet.backend.config.TestSecurityConfig;
import com.easydiet.backend.config.TestSecurityUtils;
import com.easydiet.backend.dto.diet.DietPlanTotalsResponse;
import com.easydiet.backend.service.diet.DietPlanTotalsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DietPlanTotalsControllerV1.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class DietPlanTotalsControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DietPlanTotalsService dietPlanTotalsService;

    @BeforeEach
    void setup() {
        TestSecurityUtils.mockAuthenticatedUser(
                UUID.fromString("11111111-1111-1111-1111-111111111111")
        );
    }

    @Test
    void shouldReturnDailyAndWeeklyTotalsForActiveDietPlan() throws Exception {

        DietPlanTotalsResponse response =
                new DietPlanTotalsResponse(
                        Map.of(
                                DayOfWeek.MONDAY,
                                new DietPlanTotalsResponse.DayTotals(
                                        BigDecimal.valueOf(1200),
                                        BigDecimal.valueOf(100),
                                        BigDecimal.valueOf(150),
                                        BigDecimal.valueOf(40)
                                )
                        ),
                        BigDecimal.valueOf(1200),
                        BigDecimal.valueOf(100),
                        BigDecimal.valueOf(150),
                        BigDecimal.valueOf(40)
                );

        when(dietPlanTotalsService.calculateForActivePlan(any()))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/v1/diet-plans/active/totals")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weeklyCalories").value(1200))
                .andExpect(jsonPath("$.weeklyProtein").value(100))
                .andExpect(jsonPath("$.weeklyCarbs").value(150))
                .andExpect(jsonPath("$.weeklyFat").value(40))
                .andExpect(jsonPath("$.dailyTotals.MONDAY.calories").value(1200))
                .andExpect(jsonPath("$.dailyTotals.MONDAY.protein").value(100))
                .andExpect(jsonPath("$.dailyTotals.MONDAY.carbs").value(150))
                .andExpect(jsonPath("$.dailyTotals.MONDAY.fat").value(40));
    }
}
