package com.easydiet.backend.engine.nutrition.macro;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CarnivoreDietMacroStrategyTest {

    private final CarnivoreDietMacroStrategy strategy =
            new CarnivoreDietMacroStrategy();

    @Test
    void shouldAlmostEliminateCarbs() {

        int fat = strategy.calculateFatGrams(2400);
        int carbs = strategy.calculateCarbsGrams(2400, 180, fat);

        assertThat(carbs).isLessThanOrEqualTo(10);
    }
}
