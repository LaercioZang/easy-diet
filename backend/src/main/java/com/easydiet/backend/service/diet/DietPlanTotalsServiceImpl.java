package com.easydiet.backend.service.diet;

import com.easydiet.backend.domain.diet.totals.DietDayTotals;
import com.easydiet.backend.domain.diet.totals.DietPlanTotals;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.persistence.diet.DietPlanStatus;
import com.easydiet.backend.persistence.diet.DietPlanRepository;
import com.easydiet.backend.persistence.meal.MealEntity;
import com.easydiet.backend.persistence.meal.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DietPlanTotalsServiceImpl implements DietPlanTotalsService {

    private final DietPlanRepository dietPlanRepository;
    private final MealRepository mealRepository;

    @Override
    @Transactional(readOnly = true)
    public DietPlanTotals calculateForActivePlan(UUID userId) {

        DietPlanEntity activePlan = dietPlanRepository
                .findByUserIdAndStatus(userId, DietPlanStatus.ACTIVE)
                .orElseThrow(() -> new DomainException(ErrorCode.RESOURCE_NOT_FOUND));

        List<MealEntity> meals =
                mealRepository.findAllByDietPlanId(activePlan.getId());

        Map<DayOfWeek, TotalsAccumulator> perDay = new EnumMap<>(DayOfWeek.class);

        for (MealEntity meal : meals) {
            perDay
                .computeIfAbsent(meal.getDayOfWeek(), d -> new TotalsAccumulator())
                .addMeal(meal);
        }

        List<DietDayTotals> dayTotals = new ArrayList<>();

        BigDecimal weekCalories = BigDecimal.ZERO;
        BigDecimal weekProtein = BigDecimal.ZERO;
        BigDecimal weekCarbs = BigDecimal.ZERO;
        BigDecimal weekFat = BigDecimal.ZERO;

        for (Map.Entry<DayOfWeek, TotalsAccumulator> entry : perDay.entrySet()) {

            TotalsAccumulator acc = entry.getValue();

            dayTotals.add(
                    DietDayTotals.builder()
                            .day(entry.getKey())
                            .calories(acc.calories)
                            .protein(acc.protein)
                            .carbs(acc.carbs)
                            .fat(acc.fat)
                            .build()
            );

            weekCalories = weekCalories.add(acc.calories);
            weekProtein = weekProtein.add(acc.protein);
            weekCarbs = weekCarbs.add(acc.carbs);
            weekFat = weekFat.add(acc.fat);
        }

        return DietPlanTotals.builder()
                .totalCalories(weekCalories)
                .totalProtein(weekProtein)
                .totalCarbs(weekCarbs)
                .totalFat(weekFat)
                .days(dayTotals)
                .build();
    }

    /**
     * Helper interno – não vaza para fora do service
     */
    private static class TotalsAccumulator {

        BigDecimal calories = BigDecimal.ZERO;
        BigDecimal protein = BigDecimal.ZERO;
        BigDecimal carbs = BigDecimal.ZERO;
        BigDecimal fat = BigDecimal.ZERO;

        void addMeal(MealEntity meal) {
            calories = calories.add(meal.getTotalCalories());
            protein = protein.add(meal.getTotalProtein());
            carbs = carbs.add(meal.getTotalCarbs());
            fat = fat.add(meal.getTotalFat());
        }
    }
}
