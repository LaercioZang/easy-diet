package com.easydiet.backend.engine.week;

import com.easydiet.backend.domain.diet.NutritionTarget;
import com.easydiet.backend.engine.week.model.DayDistribution;
import com.easydiet.backend.engine.week.model.DayType;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DefaultWeekDistributionEngineTest {

    private DefaultWeekDistributionEngine engine;

    @BeforeEach
    void setup() {
        engine = new DefaultWeekDistributionEngine();
    }

    @Test
    void shouldCreateWeekWithTrainingAndRestDays() {

        NutritionTarget target = NutritionTarget.builder()
                .calories(2400)
                .proteinGrams(160)
                .carbsGrams(240)
                .fatGrams(80)
                .build();

        WeekDistribution plan = engine.distribute(
                target,
                4,
                4
        );

        assertThat(plan.getDays()).hasSize(7);
        assertThat(plan.getTrainingDays()).isEqualTo(4);
        assertThat(plan.getRestDays()).isEqualTo(3);

        long trainingCount = plan.getDays().stream()
                .filter(d -> d.getDayType() == DayType.TRAINING)
                .count();

        long restCount = plan.getDays().stream()
                .filter(d -> d.getDayType() == DayType.REST)
                .count();

        assertThat(trainingCount).isEqualTo(4);
        assertThat(restCount).isEqualTo(3);
    }

    @Test
    void shouldReduceCaloriesOnRestDays() {

        NutritionTarget target = NutritionTarget.builder()
                .calories(2400)
                .proteinGrams(160)
                .carbsGrams(240)
                .fatGrams(80)
                .build();

        WeekDistribution plan = engine.distribute(
                target,
                1,
                3
        );

        DayDistribution restDay = plan.getDays().stream()
                .filter(d -> d.getDayType() == DayType.REST)
                .findFirst()
                .orElseThrow();

        int restDayCalories =
                restDay.getMeals().getMeals().get(0).getCalories();

        assertThat(restDayCalories).isLessThan(800); // 2400 / 3 = 800
    }

    @Test
    void shouldFailIfTrainingDaysInvalid() {

        NutritionTarget target = NutritionTarget.builder()
                .calories(2000)
                .proteinGrams(150)
                .carbsGrams(200)
                .fatGrams(70)
                .build();

        assertThatThrownBy(() ->
                engine.distribute(target, 8, 3)
        )
        .isInstanceOf(DomainException.class)
        .extracting("errorCode")
        .isEqualTo(ErrorCode.OUT_OF_RANGE);
    }
}
