package com.easydiet.backend.service;

import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.domain.diet.calculation.TdeeCalculator;
import com.easydiet.backend.domain.diet.command.DietPlanGenerateCommand;
import com.easydiet.backend.engine.orchestrator.DietPlanOrchestrator;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DietPlanServiceImpl implements DietPlanService {

    private final DietPlanOrchestrator dietPlanOrchestrator;
    private final TdeeCalculator tdeeCalculator;

    @Override
    public DietPlan generate(DietPlanGenerateCommand command) {

        int tdee = tdeeCalculator.calculate(
                command.bodyProfile(),
                command.activityLevel()
        );

        WeekDistribution weekDistribution =
                dietPlanOrchestrator.generateWeeklyPlan(
                        command.bodyProfile().weightKg().doubleValue(),
                        tdee,
                        command.goal(),
                        command.dietType(),
                        command.mealsPerDay(),
                        command.trainingDays().size()
                );

        return new DietPlan(tdee, weekDistribution);
    }
}
