package com.easydiet.backend.controller.v1;

import com.easydiet.backend.config.TestSecurityConfig;
import com.easydiet.backend.config.TestSecurityUtils;
import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.domain.diet.enums.Goal;
import com.easydiet.backend.domain.user.enums.ActivityLevel;
import com.easydiet.backend.domain.user.enums.Gender;
import com.easydiet.backend.dto.DietPlanGenerateRequest;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.service.DietPlanService;
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
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DietPlanControllerV1.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class DietPlanControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DietPlanService dietPlanService;

    @BeforeEach
    void setup() {
        TestSecurityUtils.mockAuthenticatedUser(
                UUID.fromString("11111111-1111-1111-1111-111111111111")
        );
    }

    @Test
    void shouldGenerateDietPlan() throws Exception {

        UUID userId = UUID.randomUUID();

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

        when(dietPlanService.generate(any(), any(UUID.class)))
                .thenReturn(plan);

        mockMvc.perform(
                        post("/api/v1/diet-plans/generate")
                                .header("X-User-Id", userId.toString()) // ✅ HEADER
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tdee").value(2600));
    }

    @Test
    void shouldReturn400WhenRequestIsInvalid() throws Exception {

        UUID userId = UUID.randomUUID();

        DietPlanGenerateRequest request = DietPlanGenerateRequest.builder()
                .weightKg(new BigDecimal("75"))
                .heightCm(178)
                .age(24)
                .mealsPerDay(5)
                .trainingDays(Set.of(DayOfWeek.MONDAY))
                .build();

        mockMvc.perform(
                        post("/api/v1/diet-plans/generate")
                                .header("X-User-Id", userId.toString()) // ✅ HEADER
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_ARGUMENT"));
    }

    @Test
    void shouldReturnDietPlanSnapshotById() throws Exception {

        UUID id = UUID.randomUUID();

        DietPlanEntity entity = DietPlanEntity.builder()
                .id(id)
                .tdee(2500)
                .weekDistributionJson("{\"ok\":true}")
                .createdAt(Instant.now())
                .build();

        when(dietPlanService.findById(id)).thenReturn(entity);

        mockMvc.perform(get("/api/v1/diet-plans/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.tdee").value(2500));
    }

    @Test
    void shouldReturnListOfDietPlanSnapshots() throws Exception {

        when(dietPlanService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/diet-plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldReturnDietPlansByUser() throws Exception {
        DietPlanEntity entity = DietPlanEntity.builder()
                .id(UUID.randomUUID())
                .tdee(2500)
                .weekDistributionJson("{}")
                .createdAt(Instant.now())
                .build();

        when(dietPlanService.findAllByUser(any(UUID.class)))
                .thenReturn(List.of(entity));

        mockMvc.perform(
                        get("/api/v1/diet-plans/user")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].tdee").value(2500));
    }
}
