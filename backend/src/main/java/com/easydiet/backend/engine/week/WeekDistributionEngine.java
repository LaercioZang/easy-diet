package com.easydiet.backend.engine.week;

import com.easydiet.backend.domain.diet.NutritionTarget;
import com.easydiet.backend.engine.week.model.WeekDistribution;

public interface WeekDistributionEngine {

    WeekDistribution distribute(
            NutritionTarget baseTarget,
            int trainingDaysPerWeek,
            int mealsPerDay
    );
}
