package com.easydiet.backend.engine.week.model;

import com.easydiet.backend.engine.meal.model.MealDistribution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayDistribution {

    private DayType dayType;
    private MealDistribution meals;
}
