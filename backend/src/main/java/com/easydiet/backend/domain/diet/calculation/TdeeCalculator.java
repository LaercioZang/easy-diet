package com.easydiet.backend.domain.diet.calculation;

import com.easydiet.backend.domain.user.BodyProfile;
import com.easydiet.backend.domain.user.enums.ActivityLevel;

public interface TdeeCalculator {

    int calculate(BodyProfile bodyProfile, ActivityLevel activityLevel);
}
