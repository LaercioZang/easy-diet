package com.easydiet.backend.engine.nutrition.macro;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class LowCarbDietMacroStrategyTest {

    private final LowCarbDietMacroStrategy strategy =
            new LowCarbDietMacroStrategy();

    @Test
    void shouldLimitCarbsToMax120g() {

        int fat = strategy.calculateFatGrams(2400);
        int carbs = strategy.calculateCarbsGrams(2400, 150, fat);

        assertThat(carbs).isLessThanOrEqualTo(120);
    }
}
