package com.easydiet.backend.engine.adjustment;

import com.easydiet.backend.domain.diet.enums.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DefaultCalorieAdjustmentEngineTest {

    private DefaultCalorieAdjustmentEngine engine;

    @BeforeEach
    void setup() {
        engine = new DefaultCalorieAdjustmentEngine();
    }

    @Test
    @DisplayName("Should increase calories by 10% for BULK goal")
    void shouldIncreaseCaloriesForBulk() {

        int baseCalories = 2500;

        int adjustedCalories = engine.adjustCalories(
            baseCalories,
            Goal.BULK
        );

        assertThat(adjustedCalories).isEqualTo(2750);
    }

    @Test
    @DisplayName("Should reduce calories by 15% for CUT goal")
    void shouldReduceCaloriesForCut() {

        int baseCalories = 2500;

        int adjustedCalories = engine.adjustCalories(
            baseCalories,
            Goal.CUT
        );

        assertThat(adjustedCalories).isEqualTo(2125);
    }

    @Test
    @DisplayName("Should keep calories unchanged for MAINTENANCE")
    void shouldKeepCaloriesForMaintenance() {

        int baseCalories = 2500;

        int adjustedCalories = engine.adjustCalories(
            baseCalories,
            Goal.MAINTENANCE
        );

        assertThat(adjustedCalories).isEqualTo(baseCalories);
    }
}
