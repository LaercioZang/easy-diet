package com.easydiet.backend.domain.plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayPlan {

    private DayOfWeek dayOfWeek;
    private boolean trainingDay;
    private int caloriesTarget;

    private List<Meal> meals;
}
