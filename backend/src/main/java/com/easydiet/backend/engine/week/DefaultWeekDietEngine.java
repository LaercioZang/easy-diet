package com.easydiet.backend.engine.week;

import com.easydiet.backend.domain.plan.DayPlan;
import com.easydiet.backend.domain.plan.Meal;
import com.easydiet.backend.domain.plan.WeekPlan;
import com.easydiet.backend.dto.WeekPlanRequest;
import com.easydiet.backend.engine.week.model.DayCalorieFactor;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class DefaultWeekDietEngine implements WeekDietEngine {

    @Override
    public WeekPlan generate(WeekPlanRequest request) {

        List<DayPlan> days = createWeekDays(request);

        return WeekPlan.builder()
                .dietType(null)            // será preenchido em outra fase
                .nutritionTarget(null)     // será preenchido em outra fase
                .days(days)
                .build();
    }

    private List<DayPlan> createWeekDays(WeekPlanRequest request) {

        Set<DayOfWeek> trainingDays = parseTrainingDays(request.getTrainingDays());
        int baseCalories = request.getCalories();
        int mealsPerDay = request.getMealsPerDay();

        List<DayPlan> days = new ArrayList<>();

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {

            boolean trainingDay = trainingDays.contains(dayOfWeek);
            int dailyCalories = calculateDailyCalories(baseCalories, trainingDay);

            DayPlan dayPlan = DayPlan.builder()
                    .dayOfWeek(dayOfWeek)
                    .trainingDay(trainingDay)
                    .caloriesTarget(dailyCalories)
                    .meals(createMeals(mealsPerDay))
                    .build();

            days.add(dayPlan);
        }

        return days;
    }

    private Set<DayOfWeek> parseTrainingDays(Set<String> trainingDays) {

        Set<DayOfWeek> result = EnumSet.noneOf(DayOfWeek.class);

        if (trainingDays == null) {
            return result;
        }

        for (String day : trainingDays) {
            result.add(DayOfWeek.valueOf(day));
        }

        return result;
    }

    private int calculateDailyCalories(int baseCalories, boolean trainingDay) {

        double factor = (trainingDay
                        ? DayCalorieFactor.TRAINING
                        : DayCalorieFactor.REST).ordinal();

        return (int) Math.round(baseCalories * factor);
    }

    private List<Meal> createMeals(int mealsPerDay) {

        List<Meal> meals = new ArrayList<>();

        for (int i = 1; i <= mealsPerDay; i++) {
            meals.add(
                    Meal.builder()
                            .name("Meal " + i)
                            .foods(List.of())
                            .build()
            );
        }

        return meals;
    }
}
