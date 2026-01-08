package com.easydiet.backend.domain.validation;

import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.engine.meal.model.MealDistribution;
import com.easydiet.backend.engine.meal.model.MealTarget;
import com.easydiet.backend.engine.week.model.DayDistribution;
import com.easydiet.backend.engine.week.model.DayType;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DomainValidationOrchestratorTest {

    @Test
    void shouldAcceptFullyValidDietPlan() {
        MealTarget meal = MealTarget.builder()
                .calories(600)
                .proteinGrams(40)
                .carbsGrams(60)
                .fatGrams(20)
                .build();

        MealDistribution meals = MealDistribution.builder()
                .mealsPerDay(1)
                .meals(List.of(meal))
                .build();

        DayDistribution day = DayDistribution.builder()
                .dayType(DayType.TRAINING)
                .meals(meals)
                .build();

        WeekDistribution week = WeekDistribution.builder()
                .trainingDays(1)
                .restDays(0)
                .days(List.of(day))
                .build();

        DietPlan plan = new DietPlan(2500, week);

        assertDoesNotThrow(() -> DomainValidationOrchestrator.validate(plan));
    }

    @Test
    void shouldFailWhenMealTargetIsInvalid() {
        MealTarget invalidMeal = MealTarget.builder()
                .calories(0)
                .proteinGrams(30)
                .carbsGrams(40)
                .fatGrams(10)
                .build();

        MealDistribution meals = MealDistribution.builder()
                .mealsPerDay(1)
                .meals(List.of(invalidMeal))
                .build();

        DayDistribution day = DayDistribution.builder()
                .dayType(DayType.TRAINING)
                .meals(meals)
                .build();

        WeekDistribution week = WeekDistribution.builder()
                .trainingDays(1)
                .restDays(0)
                .days(List.of(day))
                .build();

        DietPlan plan = new DietPlan(2500, week);

        assertThrows(IllegalStateException.class,
                () -> DomainValidationOrchestrator.validate(plan));
    }
}
