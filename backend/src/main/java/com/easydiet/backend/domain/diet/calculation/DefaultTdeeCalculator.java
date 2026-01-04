package com.easydiet.backend.domain.diet.calculation;

import com.easydiet.backend.domain.user.BodyProfile;
import com.easydiet.backend.domain.user.enums.ActivityLevel;
import com.easydiet.backend.domain.user.enums.Gender;

public class DefaultTdeeCalculator implements TdeeCalculator {

    @Override
    public int calculate(BodyProfile bodyProfile, ActivityLevel activityLevel) {

        double bmr = calculateBmr(bodyProfile);

        double multiplier = switch (activityLevel) {
            case SEDENTARY -> 1.2;
            case LIGHT -> 1.375;
            case MODERATE -> 1.55;
            case HIGH -> 1.725;
        };

        return (int) Math.round(bmr * multiplier);
    }

    private double calculateBmr(BodyProfile bodyProfile) {

        int genderFactor = bodyProfile.gender() == Gender.MALE ? 5 : -161;

        // Fórmula de Mifflin-St Jeor
        return (10 * bodyProfile.weightKg().doubleValue())
                + (6.25 * bodyProfile.heightCm())
                - (5 * bodyProfile.age())
                + genderFactor;
    }
}
