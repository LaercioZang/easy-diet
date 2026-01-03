package com.easydiet.backend.engine.adjustment;

import com.easydiet.backend.domain.diet.enums.Goal;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;

public class DefaultCalorieAdjustmentEngine
        implements CalorieAdjustmentEngine {

    private static final double BULK_FACTOR = 1.10;
    private static final double CUT_FACTOR = 0.85;

    @Override
    public int adjustCalories(int baseCalories, Goal goal) {

        if (baseCalories <= 0) {
            throw new DomainException(ErrorCode.INVALID_BASE_CALORIES);
        }

        if (goal == null) {
            throw new DomainException(ErrorCode.NULL_VALUE);
        }

        return switch (goal) {
            case BULK -> (int) Math.round(baseCalories * BULK_FACTOR);
            case CUT -> (int) Math.round(baseCalories * CUT_FACTOR);
            case MAINTENANCE -> baseCalories;
        };
    }
}
