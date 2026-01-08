package com.easydiet.backend.mapper.diet;

import com.easydiet.backend.dto.diet.DayPlanResponse;
import com.easydiet.backend.dto.diet.DietPlanResponse;
import com.easydiet.backend.dto.diet.MacrosResponse;
import com.easydiet.backend.dto.diet.MealResponse;
import com.easydiet.backend.engine.meal.model.MealDistribution;
import com.easydiet.backend.engine.meal.model.MealTarget;
import com.easydiet.backend.engine.week.model.DayDistribution;
import com.easydiet.backend.engine.week.model.DayType;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.time.DayOfWeek;
import java.util.List;

@UtilityClass
public class DietPlanResponseMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static DietPlanResponse toResponse(DietPlanEntity entity) {

        WeekDistribution week = parseWeek(entity.getWeekDistributionJson());

        List<DayPlanResponse> days = week.getDays().stream()
                .map(DietPlanResponseMapper::toDayResponse)
                .toList();

        return new DietPlanResponse(
                entity.getTdee(),
                entity.getTdee(), // targetCalories (por enquanto igual ao tdee)
                null,             // macros do plano ainda não existem no engine
                days
        );
    }

    private static DayPlanResponse toDayResponse(DayDistribution day) {

        MealDistribution mealDistribution = day.getMeals();

        List<MealResponse> meals = mealDistribution.getMeals().stream()
                .map(DietPlanResponseMapper::toMealResponse)
                .toList();

        return new DayPlanResponse(
                resolveDayOfWeek(day),
                day.getDayType().equals(DayType.TRAINING),
                meals
        );
    }

    private static MealResponse toMealResponse(MealTarget meal) {

        return new MealResponse(
                0, // mealNumber ainda não existe no modelo (posição lógica futura)
                meal.getCalories(),
                new MacrosResponse(
                        meal.getProteinGrams(),
                        meal.getCarbsGrams(),
                        meal.getFatGrams()
                )
        );
    }

    private static DayOfWeek resolveDayOfWeek(DayDistribution day) {
        // ⚠️ Engine ainda não define o dia da semana explicitamente
        // Placeholder até evolução do modelo
        return DayOfWeek.MONDAY;
    }

    private static WeekDistribution parseWeek(String json) {
        try {
            return objectMapper.readValue(json, WeekDistribution.class);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid weekDistributionJson", e);
        }
    }
}
