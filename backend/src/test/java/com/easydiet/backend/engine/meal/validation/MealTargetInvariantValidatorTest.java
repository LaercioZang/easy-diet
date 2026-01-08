package com.easydiet.backend.engine.meal.validation;

import com.easydiet.backend.engine.meal.model.MealTarget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MealTargetInvariantValidatorTest {

    @Test
    void shouldAcceptValidMealTarget() {
        MealTarget target = MealTarget.builder()
                .calories(600)
                .proteinGrams(40)
                .carbsGrams(60)
                .fatGrams(20)
                .build();

        assertDoesNotThrow(() -> MealTargetInvariantValidator.validate(target));
    }

    @Test
    void shouldFailWhenCaloriesAreZeroOrNegative() {
        MealTarget target = MealTarget.builder()
                .calories(0)
                .proteinGrams(30)
                .carbsGrams(40)
                .fatGrams(10)
                .build();

        assertThrows(IllegalStateException.class,
                () -> MealTargetInvariantValidator.validate(target));
    }

    @Test
    void shouldFailWhenMacrosAreNegative() {
        MealTarget target = MealTarget.builder()
                .calories(500)
                .proteinGrams(-10)
                .carbsGrams(30)
                .fatGrams(10)
                .build();

        assertThrows(IllegalStateException.class,
                () -> MealTargetInvariantValidator.validate(target));
    }
}
