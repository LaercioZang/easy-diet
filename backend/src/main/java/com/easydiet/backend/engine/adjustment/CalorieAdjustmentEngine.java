package com.easydiet.backend.engine.adjustment;

import com.easydiet.backend.domain.diet.enums.Goal;

public interface CalorieAdjustmentEngine {

    int adjustCalories(int baseCalories, Goal goal);
}
