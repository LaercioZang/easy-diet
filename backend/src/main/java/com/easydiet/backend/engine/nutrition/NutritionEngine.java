package com.easydiet.backend.engine.nutrition;

import com.easydiet.backend.domain.diet.NutritionTarget;
import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.domain.diet.enums.Goal;

public interface NutritionEngine {

    NutritionTarget calculate(
            double weightKg,
            int calories,
            Goal goal,
            DietCode dietCode
    );
}
