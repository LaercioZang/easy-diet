package com.easydiet.backend.engine.meal.validation;

import com.easydiet.backend.engine.meal.model.MealDistribution;
import com.easydiet.backend.engine.meal.model.MealTarget;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MealDistributionInvariantValidatorTest {

    @Test
    void shouldAcceptValidMealDistribution() {
        MealDistribution distribution = MealDistribution.builder()
                .mealsPerDay(2)
                .meals(List.of(
                        MealTarget.builder().calories(500).build(),
                        MealTarget.builder().calories(600).build()
                ))
                .build();

        assertDoesNotThrow(() -> MealDistributionInvariantValidator.validate(distribution));
    }

    @Test
    void shouldFailWhenMealsPerDayIsZero() {
        MealDistribution distribution = MealDistribution.builder()
                .mealsPerDay(0)
                .meals(List.of())
                .build();

        assertThrows(IllegalStateException.class,
                () -> MealDistributionInvariantValidator.validate(distribution));
    }

    @Test
    void shouldFailWhenMealsCountDoesNotMatchMealsPerDay() {
        MealDistribution distribution = MealDistribution.builder()
                .mealsPerDay(3)
                .meals(List.of(
                        MealTarget.builder().calories(500).build()
                ))
                .build();

        assertThrows(IllegalStateException.class,
                () -> MealDistributionInvariantValidator.validate(distribution));
    }
}
