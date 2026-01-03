package com.easydiet.backend.engine.week;

import com.easydiet.backend.domain.plan.WeekPlan;
import com.easydiet.backend.dto.WeekPlanRequest;

public interface WeekDietEngine {

    WeekPlan generate(WeekPlanRequest request);

}
