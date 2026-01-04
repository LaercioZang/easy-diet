package com.easydiet.backend.domain.diet;

import com.easydiet.backend.engine.week.model.WeekDistribution;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DietPlan {

    private final int tdee;
    private final WeekDistribution weekDistribution;
}