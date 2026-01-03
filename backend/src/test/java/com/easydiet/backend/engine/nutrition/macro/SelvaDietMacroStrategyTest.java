package com.easydiet.backend.engine.nutrition.macro;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SelvaDietMacroStrategyTest {

    private final SelvaDietMacroStrategy strategy =
            new SelvaDietMacroStrategy();

    @Test
    void shouldHaveLowerFatPercentage() {

        int fat = strategy.calculateFatGrams(2400);
        int carbs = strategy.calculateCarbsGrams(2400, 150, fat);

        assertThat(fat).isLessThan(80);
        assertThat(carbs).isGreaterThan(0);
    }
}
