package com.easydiet.backend.engine.nutrition.macro;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class KetoDietMacroStrategyTest {

    private final KetoDietMacroStrategy strategy =
            new KetoDietMacroStrategy();

    @Test
    void shouldLimitCarbsToMax50g() {

        int fat = strategy.calculateFatGrams(2400);
        int carbs = strategy.calculateCarbsGrams(2400, 150, fat);

        assertThat(carbs).isLessThanOrEqualTo(50);
    }
}
