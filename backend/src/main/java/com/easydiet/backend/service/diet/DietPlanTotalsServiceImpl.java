package com.easydiet.backend.service.diet;

import com.easydiet.backend.dto.diet.DietPlanTotalsResponse;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.persistence.diet.DietPlanRepository;
import com.easydiet.backend.persistence.diet.DietPlanStatus;
import com.easydiet.backend.persistence.meal.MealEntity;
import com.easydiet.backend.persistence.meal.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Application Service responsável por cálculo e agregação de totais de DietPlan.
 *
 * Observações importantes:
 * - Este service trabalha APENAS com dados já persistidos.
 * - Não cria nem modifica DietPlan.
 * - Não aplica regras nutricionais novas.
 * - Atua como serviço de leitura/agregação (read-model).
 */

@Service
@RequiredArgsConstructor
public class DietPlanTotalsServiceImpl implements DietPlanTotalsService {

    private final DietPlanRepository dietPlanRepository;
    private final MealRepository mealRepository;

    @Cacheable(
            value = "dietPlanTotalsActive",
            key = "#userId"
    )
    @Override
    @Transactional(readOnly = true)
    public DietPlanTotalsResponse calculateForActivePlan(UUID userId) {

        DietPlanEntity activePlan =
                dietPlanRepository.findByUserIdAndStatus(userId, DietPlanStatus.ACTIVE)
                        .orElseThrow(() -> new DomainException(
                                ErrorCode.RESOURCE_NOT_FOUND
                        ));

        List<MealEntity> meals =
                mealRepository.findAllByDietPlanId(activePlan.getId());

        Map<DayOfWeek, DietPlanTotalsResponse.DayTotals> dailyTotals =
                new EnumMap<>(DayOfWeek.class);

        BigDecimal weeklyCalories = BigDecimal.ZERO;
        BigDecimal weeklyProtein = BigDecimal.ZERO;
        BigDecimal weeklyCarbs = BigDecimal.ZERO;
        BigDecimal weeklyFat = BigDecimal.ZERO;

        for (MealEntity meal : meals) {

            DayOfWeek day = meal.getDayOfWeek();

            DietPlanTotalsResponse.DayTotals current =
                    dailyTotals.computeIfAbsent(
                            day,
                            d -> new DietPlanTotalsResponse.DayTotals(
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO,
                                    BigDecimal.ZERO
                            )
                    );

            BigDecimal mealCalories = meal.getTotalCalories();
            BigDecimal mealProtein = meal.getTotalProtein();
            BigDecimal mealCarbs = meal.getTotalCarbs();
            BigDecimal mealFat = meal.getTotalFat();

            DietPlanTotalsResponse.DayTotals updated =
                    new DietPlanTotalsResponse.DayTotals(
                            current.calories().add(mealCalories),
                            current.protein().add(mealProtein),
                            current.carbs().add(mealCarbs),
                            current.fat().add(mealFat)
                    );

            dailyTotals.put(day, updated);

            weeklyCalories = weeklyCalories.add(mealCalories);
            weeklyProtein = weeklyProtein.add(mealProtein);
            weeklyCarbs = weeklyCarbs.add(mealCarbs);
            weeklyFat = weeklyFat.add(mealFat);
        }

        return new DietPlanTotalsResponse(
                dailyTotals,
                weeklyCalories,
                weeklyProtein,
                weeklyCarbs,
                weeklyFat
        );
    }
}
