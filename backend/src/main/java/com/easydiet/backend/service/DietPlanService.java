package com.easydiet.backend.service;

import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.domain.diet.command.DietPlanGenerateCommand;

public interface DietPlanService {

    DietPlan generate(DietPlanGenerateCommand command);
}
