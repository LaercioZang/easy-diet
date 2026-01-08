package com.easydiet.backend.domain.diet;

import com.easydiet.backend.engine.week.model.WeekDistribution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DietPlanInvariantValidatorTest {

    @Test
    void shouldAcceptValidDietPlan() {
        DietPlan dietPlan = new DietPlan(
                2500,
                WeekDistribution.builder()
                        .trainingDays(5)
                        .restDays(2)
                        .days(java.util.List.of())
                        .build()
        );

        assertDoesNotThrow(() -> DietPlanInvariantValidator.validate(dietPlan));
    }

    @Test
    void shouldFailWhenTdeeIsZeroOrNegative() {
        DietPlan dietPlan = new DietPlan(
                0,
                WeekDistribution.builder().build()
        );

        assertThrows(IllegalStateException.class,
                () -> DietPlanInvariantValidator.validate(dietPlan));
    }

    @Test
    void shouldFailWhenWeekDistributionIsNull() {
        DietPlan dietPlan = new DietPlan(2500, null);

        assertThrows(IllegalStateException.class,
                () -> DietPlanInvariantValidator.validate(dietPlan));
    }
}
