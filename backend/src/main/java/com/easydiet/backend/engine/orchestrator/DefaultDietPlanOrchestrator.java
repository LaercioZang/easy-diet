package com.easydiet.backend.engine.orchestrator;

import com.easydiet.backend.domain.diet.NutritionTarget;
import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.domain.diet.enums.Goal;
import com.easydiet.backend.engine.adjustment.CalorieAdjustmentEngine;
import com.easydiet.backend.engine.adjustment.DefaultCalorieAdjustmentEngine;
import com.easydiet.backend.engine.meal.MealDistributionEngine;
import com.easydiet.backend.engine.meal.DefaultMealDistributionEngine;
import com.easydiet.backend.engine.nutrition.NutritionEngine;
import com.easydiet.backend.engine.nutrition.DefaultNutritionEngine;
import com.easydiet.backend.engine.week.WeekDistributionEngine;
import com.easydiet.backend.engine.week.DefaultWeekDistributionEngine;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;

public class DefaultDietPlanOrchestrator
        implements DietPlanOrchestrator {

    private final CalorieAdjustmentEngine calorieAdjustmentEngine =
            new DefaultCalorieAdjustmentEngine();

    private final NutritionEngine nutritionEngine =
            new DefaultNutritionEngine();

    private final MealDistributionEngine mealDistributionEngine =
            new DefaultMealDistributionEngine();

    private final WeekDistributionEngine weekDistributionEngine =
            new DefaultWeekDistributionEngine();

    @Override
    public WeekDistribution generateWeeklyPlan(
            double weightKg,
            int baseCalories,
            Goal goal,
            DietCode dietCode,
            int mealsPerDay,
            int trainingDaysPerWeek
    ) {

        if (weightKg <= 0 || baseCalories <= 0) {
            throw new DomainException(ErrorCode.OUT_OF_RANGE);
        }

        if (goal == null || dietCode == null) {
            throw new DomainException(ErrorCode.NULL_VALUE);
        }

        // 1️⃣ Ajuste calórico
        int adjustedCalories =
                calorieAdjustmentEngine.adjustCalories(baseCalories, goal);

        // 2️⃣ Macros diários
        NutritionTarget dailyTarget =
                nutritionEngine.calculate(
                        weightKg,
                        adjustedCalories,
                        goal,
                        dietCode
                );

        // 3️⃣ Distribuição semanal (internamente usa MealDistribution)
        return weekDistributionEngine.distribute(
                dailyTarget,
                trainingDaysPerWeek,
                mealsPerDay
        );
    }
}
