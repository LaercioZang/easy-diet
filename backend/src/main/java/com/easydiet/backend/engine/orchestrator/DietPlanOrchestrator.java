package com.easydiet.backend.engine.orchestrator;

import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.domain.diet.enums.Goal;
import com.easydiet.backend.engine.week.model.WeekDistribution;

public interface DietPlanOrchestrator {

    WeekDistribution generateWeeklyPlan(
            double weightKg,
            int baseCalories,
            Goal goal,
            DietCode dietCode,
            int mealsPerDay,
            int trainingDaysPerWeek
    );
}
