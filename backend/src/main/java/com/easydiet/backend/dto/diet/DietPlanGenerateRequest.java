package com.easydiet.backend.dto.diet;

import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.domain.diet.enums.Goal;
import com.easydiet.backend.domain.user.enums.ActivityLevel;
import com.easydiet.backend.domain.user.enums.Gender;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Set;

public record DietPlanGenerateRequest(

        @NotNull
        Goal goal,

        @NotNull
        DietCode dietType,

        @NotNull
        @DecimalMin(value = "1.0", inclusive = false)
        BigDecimal weightKg,

        @NotNull
        @Min(50)
        Integer heightCm,

        @NotNull
        @Min(10)
        Integer age,

        @NotNull
        Gender gender,

        @NotNull
        ActivityLevel activityLevel,

        @NotNull
        @Min(1)
        Integer mealsPerDay,

        @NotNull
        Set<DayOfWeek> trainingDays
) {}
