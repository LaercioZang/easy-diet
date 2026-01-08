package com.easydiet.backend.persistence.meal;

import com.easydiet.backend.persistence.meal.projection.DietTotalsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

public interface MealRepository extends JpaRepository<MealEntity, UUID> {

    @EntityGraph(attributePaths = {
            "foodItems",
            "foodItems.food"
    })
    List<MealEntity> findAllByDietPlanIdAndDayOfWeekOrderByMealOrder(
            UUID dietPlanId,
            DayOfWeek dayOfWeek
    );

    @EntityGraph(attributePaths = {
            "foodItems",
            "foodItems.food"
    })
    List<MealEntity> findAllByDietPlanId(UUID dietPlanId);

    @EntityGraph(attributePaths = {
            "foodItems",
            "foodItems.food"
    })
    Page<MealEntity> findAllByDietPlanId(
            UUID dietPlanId,
            Pageable pageable
    );


    @Query("""
    SELECT
        SUM(fi.calories) AS calories,
        SUM(fi.proteinGrams) AS protein,
        SUM(fi.carbsGrams) AS carbs,
        SUM(fi.fatGrams) AS fat
    FROM meal m
    JOIN m.food_item fi
    WHERE m.dietPlan.id = :dietPlanId
      AND m.dayOfWeek = :dayOfWeek
""")
    DietTotalsProjection sumTotalsByDietPlanAndDay(
            @Param("dietPlanId") UUID dietPlanId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek
    );
}
