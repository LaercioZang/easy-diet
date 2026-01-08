package com.easydiet.backend.service.diet;

import com.easydiet.backend.domain.diet.totals.DietPlanTotals;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.persistence.diet.DietPlanRepository;
import com.easydiet.backend.persistence.diet.DietPlanStatus;
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
class DietPlanTotalsServiceTest {

    @Autowired
    private DietPlanTotalsService dietPlanTotalsService;

    @Autowired
    private DietPlanRepository dietPlanRepository;

    @Autowired
    private MealRepository mealRepository;

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
                        .status(DietPlanStatus.ACTIVE)
                        .tdee(2500)
                        .weekDistributionJson("{}")
                        .createdAt(Instant.now())
                        .build()
        );
    }

    @Test
    void shouldCalculateDailyAndWeeklyTotals() {

        DietPlanEntity plan = createActivePlan();

        mealRepository.save(
                MealEntity.builder()
                        .id(UUID.randomUUID())
                        .dietPlan(plan)
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .name("Breakfast")
                        .mealOrder(1)
                        .totalCalories(new BigDecimal("400"))
                        .totalProtein(new BigDecimal("30"))
                        .totalCarbs(new BigDecimal("40"))
                        .totalFat(new BigDecimal("10"))
                        .createdAt(Instant.now())
                        .build()
        );

        mealRepository.save(
                MealEntity.builder()
                        .id(UUID.randomUUID())
                        .dietPlan(plan)
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .name("Lunch")
                        .mealOrder(2)
                        .totalCalories(new BigDecimal("600"))
                        .totalProtein(new BigDecimal("40"))
                        .totalCarbs(new BigDecimal("60"))
                        .totalFat(new BigDecimal("20"))
                        .createdAt(Instant.now())
                        .build()
        );

        mealRepository.save(
                MealEntity.builder()
                        .id(UUID.randomUUID())
                        .dietPlan(plan)
                        .dayOfWeek(DayOfWeek.TUESDAY)
                        .name("Dinner")
                        .mealOrder(1)
                        .totalCalories(new BigDecimal("500"))
                        .totalProtein(new BigDecimal("35"))
                        .totalCarbs(new BigDecimal("50"))
                        .totalFat(new BigDecimal("15"))
                        .createdAt(Instant.now())
                        .build()
        );

        DietPlanTotals totals =
                dietPlanTotalsService.calculateForActivePlan(user.getId());

        // 🔹 Totais semanais
        assertThat(totals.totalCalories()).isEqualByComparingTo("1500");
        assertThat(totals.totalProtein()).isEqualByComparingTo("105");
        assertThat(totals.totalCarbs()).isEqualByComparingTo("150");
        assertThat(totals.totalFat()).isEqualByComparingTo("45");

        // 🔹 Totais por dia
        assertThat(totals.days()).hasSize(2);

        var monday = totals.days().stream()
                .filter(d -> d.day() == DayOfWeek.MONDAY)
                .findFirst()
                .orElseThrow();

        assertThat(monday.calories()).isEqualByComparingTo("1000");
        assertThat(monday.protein()).isEqualByComparingTo("70");

        var tuesday = totals.days().stream()
                .filter(d -> d.day() == DayOfWeek.TUESDAY)
                .findFirst()
                .orElseThrow();

        assertThat(tuesday.calories()).isEqualByComparingTo("500");
        assertThat(tuesday.protein()).isEqualByComparingTo("35");
    }

    @Test
    void shouldFailWhenNoActiveDietPlanExists() {

        assertThatThrownBy(() ->
                dietPlanTotalsService.calculateForActivePlan(user.getId())
        )
        .isInstanceOf(DomainException.class);
    }
}
