package com.easydiet.backend.engine.nutrition.macro;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class VegetarianDietMacroStrategyTest {

    private final VegetarianDietMacroStrategy strategy =
            new VegetarianDietMacroStrategy();

    @Test
    void shouldFavorCarbsSlightly() {

        int fat = strategy.calculateFatGrams(2400);
        int carbs = strategy.calculateCarbsGrams(2400, 150, fat);

        assertThat(carbs).isGreaterThan(200);
    }
}
