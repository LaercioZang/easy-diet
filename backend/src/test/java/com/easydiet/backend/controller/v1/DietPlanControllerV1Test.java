package com.easydiet.backend.controller.v1;

import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.domain.diet.enums.Goal;
import com.easydiet.backend.domain.user.enums.ActivityLevel;
import com.easydiet.backend.domain.user.enums.Gender;
import com.easydiet.backend.dto.DietPlanGenerateRequest;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import com.easydiet.backend.service.DietPlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DietPlanControllerV1.class)
class DietPlanControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DietPlanService dietPlanService;

    @Test
    void shouldGenerateDietPlan() throws Exception {

        DietPlanGenerateRequest request = DietPlanGenerateRequest.builder()
                .goal(Goal.BULK)
                .dietType(DietCode.NORMAL)
                .weightKg(new BigDecimal("75"))
                .heightCm(178)
                .age(24)
                .gender(Gender.MALE)
                .activityLevel(ActivityLevel.MODERATE)
                .mealsPerDay(5)
                .trainingDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY))
                .build();

        WeekDistribution week = mock(WeekDistribution.class);
        DietPlan plan = new DietPlan(2600, week);

        when(dietPlanService.generate(any()))
                .thenReturn(plan);

        mockMvc.perform(
                        post("/api/v1/diet-plans/generate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tdee").value(2600));
    }

    @Test
    void shouldReturn400WhenRequestIsInvalid() throws Exception {

        DietPlanGenerateRequest request = DietPlanGenerateRequest.builder()
                .weightKg(new BigDecimal("75"))
                .heightCm(178)
                .age(24)
                .mealsPerDay(5)
                .trainingDays(Set.of(DayOfWeek.MONDAY))
                .build();

        mockMvc.perform(
                        post("/api/v1/diet-plans/generate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_ARGUMENT"));
    }
}
