package com.easydiet.backend.service.meal;

import com.easydiet.backend.domain.meal.command.MealCreateCommand;
import com.easydiet.backend.domain.meal.command.MealUpdateCommand;
import com.easydiet.backend.persistence.meal.MealEntity;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

public interface MealService {

    MealEntity create(UUID userId, MealCreateCommand command);

    List<MealEntity> findByDay(UUID userId, DayOfWeek dayOfWeek);

    MealEntity update(UUID mealId, UUID userId, MealUpdateCommand command);

    void delete(UUID mealId, UUID userId);
}
