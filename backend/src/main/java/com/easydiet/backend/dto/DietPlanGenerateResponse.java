package com.easydiet.backend.dto;

import com.easydiet.backend.engine.week.model.WeekDistribution;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DietPlanGenerateResponse {

    private final int tdee;
    private final WeekDistribution week;
}
