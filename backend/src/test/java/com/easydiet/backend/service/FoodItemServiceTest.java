package com.easydiet.backend.service;

import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.persistence.diet.DietPlanRepository;
import com.easydiet.backend.persistence.diet.DietPlanStatus;
import com.easydiet.backend.persistence.food.FoodEntity;
import com.easydiet.backend.persistence.food.FoodRepository;
import com.easydiet.backend.persistence.meal.FoodItemEntity;
import com.easydiet.backend.persistence.meal.FoodItemRepository;
import com.easydiet.backend.persistence.meal.MealEntity;
import com.easydiet.backend.persistence.meal.MealRepository;
import com.easydiet.backend.persistence.user.UserEntity;
import com.easydiet.backend.persistence.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FoodItemServiceTest {

    @Autowired
    private FoodItemService foodItemService;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DietPlanRepository dietPlanRepository;

    private UserEntity user;
    private MealEntity meal;
    private FoodEntity food;

    @BeforeEach
    void setup() {

        user = userRepository.save(
                UserEntity.builder()
                        .name("User")
                        .email("user@easydiet.com")
                        .passwordHash("hash")
                        .createdAt(Instant.now())
                        .build()
        );

        DietPlanEntity plan = dietPlanRepository.save(
                DietPlanEntity.builder()
                        .user(user)
                        .status(DietPlanStatus.ACTIVE)
                        .tdee(2500)
                        .weekDistributionJson("{}")
                        .createdAt(Instant.now())
                        .build()
        );

        meal = mealRepository.save(
                MealEntity.builder()
                        .dietPlan(plan)
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .name("Lunch")
                        .mealOrder(1)
                        .totalCalories(BigDecimal.ZERO)
                        .totalProtein(BigDecimal.ZERO)
                        .totalCarbs(BigDecimal.ZERO)
                        .totalFat(BigDecimal.ZERO)
                        .build()
        );

        food = foodRepository.save(
                FoodEntity.builder()
                        .name("Chicken Breast")
                        .calories(BigDecimal.valueOf(120))
                        .protein(BigDecimal.valueOf(22))
                        .carbs(BigDecimal.ZERO)
                        .fat(BigDecimal.valueOf(2))
                        .active(true)
                        .build()
        );
    }

    @Test
    void shouldCreateFoodItemAndRecalculateMealTotals() {

        FoodItemEntity item = foodItemService.create(
                user.getId(),
                meal.getId(),
                food.getId(),
                100
        );

        assertThat(item.getCalories()).isEqualByComparingTo("120");
        assertThat(item.getProtein()).isEqualByComparingTo("22");

        MealEntity updatedMeal =
                mealRepository.findById(meal.getId()).orElseThrow();

        assertThat(updatedMeal.getTotalCalories()).isEqualByComparingTo("120");
        assertThat(updatedMeal.getTotalProtein()).isEqualByComparingTo("22");
        assertThat(updatedMeal.getTotalCarbs()).isEqualByComparingTo("0");
        assertThat(updatedMeal.getTotalFat()).isEqualByComparingTo("2");
    }

    @Test
    void shouldFailWhenMealDoesNotBelongToUser() {

        UUID otherUserId = UUID.randomUUID();

        assertThatThrownBy(() ->
                foodItemService.create(
                        otherUserId,
                        meal.getId(),
                        food.getId(),
                        100
                )
        ).isInstanceOf(DomainException.class);
    }

    @Test
    void shouldDeleteFoodItemAndRecalculateMealTotals() {

        FoodItemEntity item = foodItemService.create(
                user.getId(),
                meal.getId(),
                food.getId(),
                100
        );

        foodItemService.delete(user.getId(), item.getId());

        assertThat(foodItemRepository.findById(item.getId())).isEmpty();

        MealEntity updatedMeal =
                mealRepository.findById(meal.getId()).orElseThrow();

        assertThat(updatedMeal.getTotalCalories()).isEqualByComparingTo("0");
        assertThat(updatedMeal.getTotalProtein()).isEqualByComparingTo("0");
        assertThat(updatedMeal.getTotalCarbs()).isEqualByComparingTo("0");
        assertThat(updatedMeal.getTotalFat()).isEqualByComparingTo("0");
    }
}
