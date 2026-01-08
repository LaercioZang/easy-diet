package com.easydiet.backend.engine.week.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeekDistribution {

    private int trainingDays;
    private int restDays;
    private List<DayDistribution> days;
}
