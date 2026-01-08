package com.easydiet.backend.engine.week.validation;

import com.easydiet.backend.engine.week.model.WeekDistribution;

public final class WeekDistributionInvariantValidator {

    private WeekDistributionInvariantValidator() {
    }

    public static void validate(WeekDistribution week) {
        if (week.getTrainingDays() < 0 || week.getRestDays() < 0) {
            throw new IllegalStateException("Dias de treino e descanso não podem ser negativos");
        }

        if (week.getDays() == null || week.getDays().isEmpty()) {
            throw new IllegalStateException("WeekDistribution deve conter dias");
        }

        int totalDays = week.getTrainingDays() + week.getRestDays();

        if (totalDays != week.getDays().size()) {
            throw new IllegalStateException(
                    "Soma de trainingDays + restDays deve ser igual ao número de dias"
            );
        }
    }
}
