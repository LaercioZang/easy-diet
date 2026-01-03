package com.easydiet.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeekPlanRequest {

    private int calories;
    private int mealsPerDay;
    private Set<String> trainingDays;

}
