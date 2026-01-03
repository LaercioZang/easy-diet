package com.easydiet.backend.engine.week.model;

import com.easydiet.backend.engine.meal.model.MealDistribution;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DayDistribution {

    private DayType dayType;
    private MealDistribution meals;
}
