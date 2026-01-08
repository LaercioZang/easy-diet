package com.easydiet.backend.mapper;

import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;

public final class DietPlanPersistenceMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private DietPlanPersistenceMapper() {}

    public static DietPlanEntity toEntity(DietPlan plan) {
        try {
            return DietPlanEntity.builder()
                    .tdee(plan.getTdee())
                    .weekDistributionJson(
                            objectMapper.writeValueAsString(plan.getWeekDistribution())
                    )
                    .createdAt(Instant.now())
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize diet plan", e);
        }
    }
}
