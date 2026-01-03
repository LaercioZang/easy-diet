package com.easydiet.backend.domain.plan;

import com.easydiet.backend.domain.diet.DietType;
import com.easydiet.backend.domain.diet.NutritionTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeekPlan {

    private String id;
    private DietType dietType;
    private NutritionTarget nutritionTarget;

    private List<DayPlan> days;
}
