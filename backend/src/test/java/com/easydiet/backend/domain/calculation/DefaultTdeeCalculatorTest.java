package com.easydiet.backend.domain.calculation;

import com.easydiet.backend.domain.diet.calculation.DefaultTdeeCalculator;
import com.easydiet.backend.domain.diet.calculation.TdeeCalculator;
import com.easydiet.backend.domain.user.BodyProfile;
import com.easydiet.backend.domain.user.enums.ActivityLevel;
import com.easydiet.backend.domain.user.enums.Gender;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultTdeeCalculatorTest {

    private final TdeeCalculator calculator = new DefaultTdeeCalculator();

    @Test
    void shouldCalculateTdeeForMaleWithModerateActivity() {
        BodyProfile profile = new BodyProfile(
                new BigDecimal("80"),
                180,
                25,
                Gender.MALE
        );

        int tdee = calculator.calculate(profile, ActivityLevel.MODERATE);

        // valor aproximado, evitamos teste frágil
        assertThat(tdee).isBetween(2700, 2900);
    }

    @Test
    void shouldCalculateTdeeForFemaleWithLightActivity() {
        BodyProfile profile = new BodyProfile(
                new BigDecimal("60"),
                165,
                30,
                Gender.FEMALE
        );

        int tdee = calculator.calculate(profile, ActivityLevel.LIGHT);

        assertThat(tdee).isBetween(1700, 2100);
    }
}
