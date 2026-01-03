package com.easydiet.backend.engine.meal;

import com.easydiet.backend.domain.diet.NutritionTarget;
import com.easydiet.backend.engine.meal.model.MealDistribution;
import com.easydiet.backend.engine.meal.model.MealTarget;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DefaultMealDistributionEngineTest {

    private DefaultMealDistributionEngine engine;

    @BeforeEach
    void setup() {
        engine = new DefaultMealDistributionEngine();
    }

    @Test
    void shouldDistributeMacrosEquallyAcrossMeals() {

        NutritionTarget target = NutritionTarget.builder()
                .calories(2400)
                .proteinGrams(160)
                .carbsGrams(240)
                .fatGrams(80)
                .build();

        int mealsPerDay = 4;

        MealDistribution distribution =
                engine.distribute(target, mealsPerDay);

        assertThat(distribution.getMeals()).hasSize(mealsPerDay);

        int expectedCaloriesPerMeal = target.getCalories() / mealsPerDay;
        int expectedProteinPerMeal = target.getProteinGrams() / mealsPerDay;
        int expectedCarbsPerMeal = target.getCarbsGrams() / mealsPerDay;
        int expectedFatPerMeal = target.getFatGrams() / mealsPerDay;

        for (MealTarget meal : distribution.getMeals()) {
            assertThat(meal.getCalories()).isEqualTo(expectedCaloriesPerMeal);
            assertThat(meal.getProteinGrams()).isEqualTo(expectedProteinPerMeal);
            assertThat(meal.getCarbsGrams()).isEqualTo(expectedCarbsPerMeal);
            assertThat(meal.getFatGrams()).isEqualTo(expectedFatPerMeal);
        }

        // 🔒 NOVO: garante que não houve perda na distribuição
        int totalCalories =
                distribution.getMeals().stream()
                        .mapToInt(MealTarget::getCalories)
                        .sum();

        int totalProtein =
                distribution.getMeals().stream()
                        .mapToInt(MealTarget::getProteinGrams)
                        .sum();

        int totalCarbs =
                distribution.getMeals().stream()
                        .mapToInt(MealTarget::getCarbsGrams)
                        .sum();

        int totalFat =
                distribution.getMeals().stream()
                        .mapToInt(MealTarget::getFatGrams)
                        .sum();

        assertThat(totalCalories).isEqualTo(target.getCalories());
        assertThat(totalProtein).isEqualTo(target.getProteinGrams());
        assertThat(totalCarbs).isEqualTo(target.getCarbsGrams());
        assertThat(totalFat).isEqualTo(target.getFatGrams());
    }

    @Test
    void shouldFailIfMealsPerDayIsInvalid() {

        NutritionTarget target = NutritionTarget.builder()
                .calories(2000)
                .proteinGrams(150)
                .carbsGrams(200)
                .fatGrams(70)
                .build();

        assertThatThrownBy(() -> engine.distribute(target, 0))
                .isInstanceOf(DomainException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INVALID_MEALS_PER_DAY);
    }

    @Test
    void shouldFailIfTargetIsNull() {
        assertThatThrownBy(() -> engine.distribute(null, 3))
                .isInstanceOf(DomainException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NULL_VALUE);
    }
}
