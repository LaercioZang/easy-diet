package com.easydiet.backend.service;

import com.easydiet.backend.dto.diet.DietPlanTotalsResponse;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.persistence.diet.DietPlanRepository;
import com.easydiet.backend.persistence.diet.DietPlanStatus;
import com.easydiet.backend.persistence.meal.MealEntity;
import com.easydiet.backend.persistence.meal.MealRepository;
import com.easydiet.backend.persistence.user.UserEntity;
import com.easydiet.backend.persistence.user.UserRepository;
import com.easydiet.backend.service.diet.DietPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DietPlanServiceImplTest {

    @Autowired
    private DietPlanService dietPlanService;

    @Autowired
    private DietPlanRepository dietPlanRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;
    private DietPlanEntity activePlan;

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

        activePlan = dietPlanRepository.save(
                DietPlanEntity.builder()
                        .id(UUID.randomUUID())
                        .user(user)
                        .status(DietPlanStatus.ACTIVE)
                        .tdee(2500)
                        .weekDistributionJson("{}")
                        .createdAt(Instant.now())
                        .build()
        );

        // MONDAY
        mealRepository.save(
                MealEntity.builder()
                        .dietPlan(activePlan)
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .name("Breakfast")
                        .mealOrder(1)
                        .totalCalories(new BigDecimal("500"))
                        .totalProtein(new BigDecimal("30"))
                        .totalCarbs(new BigDecimal("50"))
                        .totalFat(new BigDecimal("10"))
                        .foodItems(new ArrayList<>())
                        .createdAt(Instant.now())
                        .build()
        );

        mealRepository.save(
                MealEntity.builder()
                        .dietPlan(activePlan)
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .name("Lunch")
                        .mealOrder(2)
                        .totalCalories(new BigDecimal("700"))
                        .totalProtein(new BigDecimal("40"))
                        .totalCarbs(new BigDecimal("80"))
                        .totalFat(new BigDecimal("20"))
                        .foodItems(new ArrayList<>())
                        .createdAt(Instant.now())
                        .build()
        );

        // TUESDAY
        mealRepository.save(
                MealEntity.builder()
                        .dietPlan(activePlan)
                        .dayOfWeek(DayOfWeek.TUESDAY)
                        .name("Dinner")
                        .mealOrder(1)
                        .totalCalories(new BigDecimal("600"))
                        .totalProtein(new BigDecimal("35"))
                        .totalCarbs(new BigDecimal("60"))
                        .totalFat(new BigDecimal("15"))
                        .foodItems(new ArrayList<>())
                        .createdAt(Instant.now())
                        .build()
        );
    }

    @Test
    void shouldCalculateDailyAndWeeklyTotals() {

        DietPlanTotalsResponse totals =
                dietPlanService.calculateTotals(user.getId());

        // DAILY TOTALS
        DietPlanTotalsResponse.DayTotals monday =
                totals.dailyTotals().get(DayOfWeek.MONDAY);

        assertThat(monday.calories()).isEqualByComparingTo("1200");
        assertThat(monday.protein()).isEqualByComparingTo("70");
        assertThat(monday.carbs()).isEqualByComparingTo("130");
        assertThat(monday.fat()).isEqualByComparingTo("30");

        DietPlanTotalsResponse.DayTotals tuesday =
                totals.dailyTotals().get(DayOfWeek.TUESDAY);

        assertThat(tuesday.calories()).isEqualByComparingTo("600");
        assertThat(tuesday.protein()).isEqualByComparingTo("35");
        assertThat(tuesday.carbs()).isEqualByComparingTo("60");
        assertThat(tuesday.fat()).isEqualByComparingTo("15");

        // WEEKLY TOTALS
        assertThat(totals.weeklyCalories()).isEqualByComparingTo("1800");
        assertThat(totals.weeklyProtein()).isEqualByComparingTo("105");
        assertThat(totals.weeklyCarbs()).isEqualByComparingTo("190");
        assertThat(totals.weeklyFat()).isEqualByComparingTo("45");
    }
}
