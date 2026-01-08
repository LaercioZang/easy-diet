package com.easydiet.backend.service;

import com.easydiet.backend.domain.meal.command.MealCreateCommand;
import com.easydiet.backend.domain.meal.command.MealUpdateCommand;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.persistence.diet.DietPlanRepository;
import com.easydiet.backend.persistence.diet.DietPlanStatus;
import com.easydiet.backend.persistence.meal.MealEntity;
import com.easydiet.backend.persistence.meal.MealRepository;
import com.easydiet.backend.persistence.user.UserEntity;
import com.easydiet.backend.persistence.user.UserRepository;
import com.easydiet.backend.service.meal.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private DietPlanRepository dietPlanRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setup() {
        user = userRepository.save(
                UserEntity.builder()
                        .id(UUID.randomUUID())
                        .name("User")
                        .email("user@easydiet.com")
                        .passwordHash("hash")
                        .createdAt(Instant.now())
                        .build()
        );
    }

    private DietPlanEntity createActivePlan() {
        return dietPlanRepository.save(
                DietPlanEntity.builder()
                        .id(UUID.randomUUID())
                        .user(user)
                        .tdee(2500)
                        .weekDistributionJson("{}")
                        .status(DietPlanStatus.ACTIVE)
                        .createdAt(Instant.now())
                        .build()
        );
    }

    @Test
    void shouldCreateMealOnActiveDietPlan() {

        createActivePlan();

        MealCreateCommand command = MealCreateCommand.builder()
                .dayOfWeek(DayOfWeek.MONDAY)
                .name("Breakfast")
                .mealOrder(1)
                .build();

        MealEntity meal = mealService.create(user.getId(), command);

        assertThat(meal.getId()).isNotNull();
        assertThat(meal.getDietPlan()).isNotNull();
        assertThat(meal.getName()).isEqualTo("Breakfast");
        assertThat(meal.getTotalCalories()).isZero();
    }

    @Test
    void shouldFailWhenNoActiveDietPlanExists() {

        MealCreateCommand command = MealCreateCommand.builder()
                .dayOfWeek(DayOfWeek.MONDAY)
                .name("Breakfast")
                .mealOrder(1)
                .build();

        assertThatThrownBy(() ->
                mealService.create(user.getId(), command)
        )
                .isInstanceOf(DomainException.class)
                .satisfies(ex -> {
                    DomainException de = (DomainException) ex;
                    assertThat(de.getErrorCode())
                            .isEqualTo(ErrorCode.RESOURCE_NOT_FOUND);
                });
    }

    @Test
    void shouldUpdateMealNameAndOrderOnly() {

        DietPlanEntity plan = createActivePlan();

        MealEntity meal = mealRepository.save(
                MealEntity.builder()
                        .dietPlan(plan)
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .name("Breakfast")
                        .mealOrder(1)
                        .totalCalories(java.math.BigDecimal.ZERO)
                        .totalProtein(java.math.BigDecimal.ZERO)
                        .totalCarbs(java.math.BigDecimal.ZERO)
                        .totalFat(java.math.BigDecimal.ZERO)
                        .foodItems(new ArrayList<>())
                        .createdAt(Instant.now())
                        .build()
        );

        MealUpdateCommand update = MealUpdateCommand.builder()
                .name("Updated Breakfast")
                .mealOrder(2)
                .build();

        MealEntity updated =
                mealService.update(meal.getId(), user.getId(), update);

        assertThat(updated.getName()).isEqualTo("Updated Breakfast");
        assertThat(updated.getMealOrder()).isEqualTo(2);
    }

    @Test
    void shouldDeleteMeal() {

        DietPlanEntity plan = createActivePlan();

        MealEntity meal = mealRepository.save(
                MealEntity.builder()
                        .dietPlan(plan)
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .name("Lunch")
                        .mealOrder(2)
                        .totalCalories(java.math.BigDecimal.ZERO)
                        .totalProtein(java.math.BigDecimal.ZERO)
                        .totalCarbs(java.math.BigDecimal.ZERO)
                        .totalFat(java.math.BigDecimal.ZERO)
                        .foodItems(new ArrayList<>())
                        .createdAt(Instant.now())
                        .build()
        );

        mealService.delete(meal.getId(), user.getId());

        assertThat(mealRepository.findById(meal.getId()))
                .isEmpty();
    }
}
