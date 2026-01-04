package com.easydiet.backend.dto;

import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.domain.diet.enums.Goal;
import com.easydiet.backend.domain.user.enums.ActivityLevel;
import com.easydiet.backend.domain.user.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Set;

@Getter
@Builder
public class DietPlanGenerateRequest {

    @NotNull
    private Goal goal;

    @NotNull
    private DietCode dietType;

    @NotNull @Positive
    private BigDecimal weightKg;

    @NotNull @Positive
    private Integer heightCm;

    @NotNull @Positive
    private Integer age;

    @NotNull
    private Gender gender;

    @NotNull
    private ActivityLevel activityLevel;

    @NotNull @Min(1)
    private Integer mealsPerDay;

    @NotNull
    private Set<DayOfWeek> trainingDays;
}
