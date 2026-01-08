package com.easydiet.backend.persistence.meal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

public interface MealRepository extends JpaRepository<MealEntity, UUID> {

    List<MealEntity> findAllByDietPlanIdAndDayOfWeekOrderByMealOrder(
            UUID dietPlanId,
            DayOfWeek dayOfWeek
    );

    List<MealEntity> findAllByDietPlanId(UUID dietPlanId);
}
