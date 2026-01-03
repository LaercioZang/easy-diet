package com.easydiet.backend.engine.nutrition;

import com.easydiet.backend.domain.diet.NutritionTarget;
import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.domain.diet.enums.Goal;
import com.easydiet.backend.engine.nutrition.macro.DietMacroStrategy;
import com.easydiet.backend.engine.nutrition.macro.DietMacroStrategyFactory;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;

public class DefaultNutritionEngine implements NutritionEngine {

    private static final double PROTEIN_PER_KG_BULK = 2.2;
    private static final double PROTEIN_PER_KG_CUT = 2.4;

    private final DietMacroStrategyFactory strategyFactory =
            new DietMacroStrategyFactory();

    @Override
    public NutritionTarget calculate(
            double weightKg,
            int calories,      // 🔒 JÁ AJUSTADAS
            Goal goal,
            DietCode dietCode
    ) {

        if (weightKg <= 0 || calories <= 0) {
            throw new DomainException(ErrorCode.OUT_OF_RANGE);
        }

        if (goal == null || dietCode == null) {
            throw new DomainException(ErrorCode.NULL_VALUE);
        }

        int proteinGrams = calculateProtein(weightKg, goal);

        DietMacroStrategy strategy =
                strategyFactory.getStrategy(dietCode);

        int fatGrams = strategy.calculateFatGrams(calories);
        int carbsGrams = strategy.calculateCarbsGrams(
                calories,
                proteinGrams,
                fatGrams
        );

        return NutritionTarget.builder()
                .calories(calories)
                .proteinGrams(proteinGrams)
                .fatGrams(fatGrams)
                .carbsGrams(carbsGrams)
                .goal(goal)
                .build();
    }

    private int calculateProtein(double weightKg, Goal goal) {
        double factor = goal == Goal.BULK
                ? PROTEIN_PER_KG_BULK
                : PROTEIN_PER_KG_CUT;

        return (int) Math.round(weightKg * factor);
    }
}
