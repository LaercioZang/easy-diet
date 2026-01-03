package com.easydiet.backend.engine.nutrition.macro;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class VeganDietMacroStrategyTest {

    private final VeganDietMacroStrategy strategy =
            new VeganDietMacroStrategy();

    @Test
    void shouldHaveHigherCarbs() {

        int fat = strategy.calculateFatGrams(2400);
        int carbs = strategy.calculateCarbsGrams(2400, 140, fat);

        assertThat(carbs).isGreaterThan(250);
    }
}
