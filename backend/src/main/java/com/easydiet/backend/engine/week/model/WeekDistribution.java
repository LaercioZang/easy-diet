package com.easydiet.backend.engine.week.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WeekDistribution {

    private int trainingDays;
    private int restDays;
    private List<DayDistribution> days;
}
