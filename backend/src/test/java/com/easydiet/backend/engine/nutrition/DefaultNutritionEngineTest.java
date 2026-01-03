package com.easydiet.backend.engine.nutrition;

import com.easydiet.backend.domain.diet.NutritionTarget;
import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.domain.diet.enums.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DefaultNutritionEngineTest {

    private DefaultNutritionEngine engine;

    @BeforeEach
    void setup() {
        engine = new DefaultNutritionEngine();
    }

    @Test
    void shouldGenerateNutritionTargetForNormalDiet() {

        NutritionTarget target = engine.calculate(
                70.0,                // weightKg
                2600,                // calories
                Goal.MAINTENANCE,
                DietCode.NORMAL
        );

        assertThat(target.getCalories()).isEqualTo(2600);
        assertThat(target.getProteinGrams()).isGreaterThan(0);
        assertThat(target.getCarbsGrams()).isGreaterThan(0);
        assertThat(target.getFatGrams()).isGreaterThan(0);
    }

    @Test
    void shouldApplyGoalDifferenceOnProtein() {

        NutritionTarget bulk = engine.calculate(
                70.0,
                2600,
                Goal.BULK,
                DietCode.NORMAL
        );

        NutritionTarget cut = engine.calculate(
                70.0,
                2600,
                Goal.CUT,
                DietCode.NORMAL
        );

        assertThat(cut.getProteinGrams())
                .isGreaterThan(bulk.getProteinGrams());
    }

    @Test
    void shouldRespectKetoCarbLimitThroughEngine() {

        NutritionTarget target = engine.calculate(
                70.0,
                2400,
                Goal.MAINTENANCE,
                DietCode.KETO
        );

        assertThat(target.getCarbsGrams()).isLessThanOrEqualTo(50);
    }
}
