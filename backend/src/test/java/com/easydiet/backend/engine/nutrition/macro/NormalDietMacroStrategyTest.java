package com.easydiet.backend.engine.nutrition.macro;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class NormalDietMacroStrategyTest {

    private final NormalDietMacroStrategy strategy =
            new NormalDietMacroStrategy();

    @Test
    void shouldCalculateBalancedMacros() {

        int fat = strategy.calculateFatGrams(2400);
        int carbs = strategy.calculateCarbsGrams(2400, 150, fat);

        assertThat(fat).isGreaterThan(0);
        assertThat(carbs).isGreaterThan(0);
    }
}
