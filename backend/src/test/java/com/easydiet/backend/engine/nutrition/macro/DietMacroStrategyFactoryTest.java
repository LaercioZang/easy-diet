package com.easydiet.backend.engine.nutrition.macro;

import com.easydiet.backend.domain.diet.enums.DietCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DietMacroStrategyFactoryTest {

    private final DietMacroStrategyFactory factory =
            new DietMacroStrategyFactory();

    @Test
    void shouldReturnNormalStrategy() {
        DietMacroStrategy strategy = factory.getStrategy(DietCode.NORMAL);
        assertThat(strategy).isInstanceOf(NormalDietMacroStrategy.class);
    }

    @Test
    void shouldReturnKetoStrategy() {
        DietMacroStrategy strategy = factory.getStrategy(DietCode.KETO);
        assertThat(strategy).isInstanceOf(KetoDietMacroStrategy.class);
    }

    @Test
    void shouldReturnLowCarbStrategy() {
        DietMacroStrategy strategy = factory.getStrategy(DietCode.LOW_CARB);
        assertThat(strategy).isInstanceOf(LowCarbDietMacroStrategy.class);
    }

    @Test
    void shouldReturnSelvaStrategy() {
        DietMacroStrategy strategy = factory.getStrategy(DietCode.SELVA);
        assertThat(strategy).isInstanceOf(SelvaDietMacroStrategy.class);
    }

    @Test
    void shouldReturnVegetarianStrategy() {
        DietMacroStrategy strategy = factory.getStrategy(DietCode.VEGETARIAN);
        assertThat(strategy).isInstanceOf(VegetarianDietMacroStrategy.class);
    }

    @Test
    void shouldReturnVeganStrategy() {
        DietMacroStrategy strategy = factory.getStrategy(DietCode.VEGAN);
        assertThat(strategy).isInstanceOf(VeganDietMacroStrategy.class);
    }

    @Test
    void shouldReturnCarnivoreStrategy() {
        DietMacroStrategy strategy = factory.getStrategy(DietCode.CARNIVORE);
        assertThat(strategy).isInstanceOf(CarnivoreDietMacroStrategy.class);
    }
}
