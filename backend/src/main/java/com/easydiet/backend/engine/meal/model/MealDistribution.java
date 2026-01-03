package com.easydiet.backend.engine.meal.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MealDistribution {

    private int mealsPerDay;
    private List<MealTarget> meals;
}
