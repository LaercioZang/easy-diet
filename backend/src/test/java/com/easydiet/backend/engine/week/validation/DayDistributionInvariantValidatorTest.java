package com.easydiet.backend.engine.week.validation;

import com.easydiet.backend.engine.week.model.DayDistribution;
import com.easydiet.backend.engine.week.model.DayType;
import com.easydiet.backend.engine.meal.model.MealDistribution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DayDistributionInvariantValidatorTest {

    @Test
    void shouldAcceptValidDayDistribution() {
        DayDistribution day = DayDistribution.builder()
                .dayType(DayType.TRAINING)
                .meals(MealDistribution.builder().build())
                .build();

        assertDoesNotThrow(() -> DayDistributionInvariantValidator.validate(day));
    }

    @Test
    void shouldFailWhenDayTypeIsNull() {
        DayDistribution day = DayDistribution.builder().build();

        assertThrows(IllegalStateException.class,
                () -> DayDistributionInvariantValidator.validate(day));
    }

    @Test
    void shouldFailWhenMealDistributionIsNull() {
        DayDistribution day = DayDistribution.builder()
                .dayType(DayType.REST)
                .build();

        assertThrows(IllegalStateException.class,
                () -> DayDistributionInvariantValidator.validate(day));
    }
}
