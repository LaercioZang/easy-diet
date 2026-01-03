package com.easydiet.backend.engine.meal;

import com.easydiet.backend.domain.diet.NutritionTarget;
import com.easydiet.backend.engine.meal.model.MealDistribution;
import com.easydiet.backend.engine.meal.model.MealTarget;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;

public class DefaultMealDistributionEngine
        implements MealDistributionEngine {

    @Override
    public MealDistribution distribute(
            NutritionTarget dailyTarget,
            int mealsPerDay
    ) {

        if (dailyTarget == null) {
            throw new DomainException(ErrorCode.NULL_VALUE);
        }

        if (mealsPerDay <= 0) {
            throw new DomainException(ErrorCode.INVALID_MEALS_PER_DAY);
        }

        int caloriesPerMeal = dailyTarget.getCalories() / mealsPerDay;
        int proteinPerMeal = dailyTarget.getProteinGrams() / mealsPerDay;
        int carbsPerMeal = dailyTarget.getCarbsGrams() / mealsPerDay;
        int fatPerMeal = dailyTarget.getFatGrams() / mealsPerDay;

        List<MealTarget> meals = new ArrayList<>();

        for (int i = 0; i < mealsPerDay; i++) {
            meals.add(MealTarget.builder()
                    .calories(caloriesPerMeal)
                    .proteinGrams(proteinPerMeal)
                    .carbsGrams(carbsPerMeal)
                    .fatGrams(fatPerMeal)
                    .build());
        }

        return MealDistribution.builder()
                .mealsPerDay(mealsPerDay)
                .meals(meals)
                .build();
    }
}
