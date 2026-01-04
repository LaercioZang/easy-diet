package com.easydiet.backend.domain.diet.command;

import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.domain.diet.enums.Goal;
import com.easydiet.backend.domain.user.BodyProfile;
import com.easydiet.backend.domain.user.enums.ActivityLevel;

import java.time.DayOfWeek;
import java.util.Set;

public record DietPlanGenerateCommand(
        Goal goal,
        DietCode dietType,
        BodyProfile bodyProfile,
        ActivityLevel activityLevel,
        int mealsPerDay,
        Set<DayOfWeek> trainingDays
) {}
