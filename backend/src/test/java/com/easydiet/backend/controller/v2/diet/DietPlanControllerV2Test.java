package com.easydiet.backend.controller.v2.diet;

import com.easydiet.backend.config.TestSecurityConfig;
import com.easydiet.backend.config.TestSecurityUtils;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.persistence.diet.DietPlanStatus;
import com.easydiet.backend.service.diet.DietPlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DietPlanControllerV2.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class DietPlanControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DietPlanService dietPlanService;

    private UUID userId;

    @BeforeEach
    void setup() {
        userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        TestSecurityUtils.mockAuthenticatedUser(userId);
    }

    @Test
    void shouldReturnActiveDietPlanV2() throws Exception {

        DietPlanEntity plan = DietPlanEntity.builder()
                .id(UUID.randomUUID())
                .user(null)
                .tdee(2500)
                .status(DietPlanStatus.ACTIVE)
                .weekDistributionJson("""
                    {
                      "trainingDays": 3,
                      "restDays": 4,
                      "days": [
                        {
                          "dayType": "TRAINING",
                          "meals": {
                            "mealsPerDay": 3,
                            "meals": [
                              { "calories": 500, "proteinGrams": 40, "carbsGrams": 50, "fatGrams": 15 },
                              { "calories": 700, "proteinGrams": 50, "carbsGrams": 80, "fatGrams": 20 },
                              { "calories": 800, "proteinGrams": 60, "carbsGrams": 100, "fatGrams": 25 }
                            ]
                          }
                        }
                      ]
                    }
                """)
                .createdAt(Instant.now())
                .build();

        when(dietPlanService.findActive(userId))
                .thenReturn(plan);

        mockMvc.perform(
                        get("/api/v2/diet-plans/active")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tdee").value(2500))
                .andExpect(jsonPath("$.days").isArray())
                .andExpect(jsonPath("$.days[0].meals").isArray())
                .andExpect(jsonPath("$.days[0].meals[0].calories").value(500))
                .andExpect(jsonPath("$.days[0].meals[0].macros.protein").value(40));
    }
}
