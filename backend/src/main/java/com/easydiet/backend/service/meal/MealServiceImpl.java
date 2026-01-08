package com.easydiet.backend.service.meal;

import com.easydiet.backend.domain.meal.command.MealCreateCommand;
import com.easydiet.backend.domain.meal.command.MealUpdateCommand;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.persistence.diet.DietPlanStatus;
import com.easydiet.backend.persistence.meal.MealEntity;
import com.easydiet.backend.persistence.meal.MealRepository;
import com.easydiet.backend.service.diet.DietPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Application Service responsável por operações de Meal.
 *
 * Responsabilidades:
 * - Orquestrar persistência e leitura de meals.
 * - Validar entradas básicas do caso de uso.
 *
 * Restrições:
 * - NÃO calcular macros, calorias ou distribuição.
 * - NÃO aplicar regras nutricionais.
 * - Qualquer lógica de domínio deve estar no Domain ou Engine apropriado.
 */

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final DietPlanService dietPlanService;

    @Override
    @CacheEvict(
            value = "dietPlanTotalsActive",
            allEntries = true
    )
    @Transactional
    public MealEntity create(UUID userId, MealCreateCommand command) {

        DietPlanEntity activePlan = dietPlanService.findActive(userId);

        MealEntity meal = MealEntity.builder()
                .dietPlan(activePlan)
                .dayOfWeek(command.dayOfWeek())
                .name(command.name())
                .mealOrder(command.mealOrder())
                .totalCalories(BigDecimal.ZERO)
                .totalProtein(BigDecimal.ZERO)
                .totalCarbs(BigDecimal.ZERO)
                .totalFat(BigDecimal.ZERO)
                .createdAt(Instant.now())
                .build();

        return mealRepository.save(meal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MealEntity> findByDay(UUID userId, DayOfWeek dayOfWeek) {

        DietPlanEntity activePlan = dietPlanService.findActive(userId);

        return mealRepository.findAllByDietPlanIdAndDayOfWeekOrderByMealOrder(
                activePlan.getId(),
                dayOfWeek
        );
    }

    @Override
    @CacheEvict(
            value = "dietPlanTotalsActive",
            allEntries = true
    )
    @Transactional
    public MealEntity update(UUID mealId, UUID userId, MealUpdateCommand command) {

        MealEntity meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new DomainException(ErrorCode.RESOURCE_NOT_FOUND));

        if (!meal.getDietPlan().getUser().getId().equals(userId)) {
            throw new DomainException(ErrorCode.INVALID_ARGUMENT);
        }

        if (meal.getDietPlan().getStatus() != DietPlanStatus.ACTIVE) {
            throw new DomainException(ErrorCode.INVALID_ARGUMENT);
        }

        meal.setName(command.name());
        meal.setMealOrder(command.mealOrder());

        return mealRepository.save(meal);
    }

    @Override
    @CacheEvict(
            value = "dietPlanTotalsActive",
            allEntries = true
    )
    @Transactional
    public void delete(UUID mealId, UUID userId) {

        MealEntity meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new DomainException(ErrorCode.RESOURCE_NOT_FOUND));

        if (!meal.getDietPlan().getUser().getId().equals(userId)) {
            throw new DomainException(ErrorCode.INVALID_ARGUMENT);
        }

        if (meal.getDietPlan().getStatus() != DietPlanStatus.ACTIVE) {
            throw new DomainException(ErrorCode.INVALID_ARGUMENT);
        }

        mealRepository.delete(meal);
    }
}
