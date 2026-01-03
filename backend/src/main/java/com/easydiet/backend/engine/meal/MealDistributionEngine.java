package com.easydiet.backend.engine.meal;

import com.easydiet.backend.domain.diet.NutritionTarget;
import com.easydiet.backend.engine.meal.model.MealDistribution;

public interface MealDistributionEngine {

    MealDistribution distribute(
            NutritionTarget dailyTarget,
            int mealsPerDay
    );
}
