package com.easydiet.backend.mapper;

import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.domain.diet.command.DietPlanGenerateCommand;
import com.easydiet.backend.domain.user.BodyProfile;
import com.easydiet.backend.dto.DietPlanGenerateRequest;
import com.easydiet.backend.dto.DietPlanGenerateResponse;

public final class DietPlanMapper {

    private DietPlanMapper() {}

    public static DietPlanGenerateCommand toCommand(DietPlanGenerateRequest request) {
        return new DietPlanGenerateCommand(
                request.getGoal(),
                request.getDietType(),
                new BodyProfile(
                        request.getWeightKg(),
                        request.getHeightCm(),
                        request.getAge(),
                        request.getGender()
                ),
                request.getActivityLevel(),
                request.getMealsPerDay(),
                request.getTrainingDays()
        );
    }

    public static DietPlanGenerateResponse toResponse(DietPlan plan) {
        return new DietPlanGenerateResponse(
                plan.getTdee(),
                plan.getWeekDistribution()
        );
    }
}
