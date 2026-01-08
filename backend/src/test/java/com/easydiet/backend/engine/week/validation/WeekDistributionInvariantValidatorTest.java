package com.easydiet.backend.engine.week.validation;

import com.easydiet.backend.engine.week.model.DayDistribution;
import com.easydiet.backend.engine.week.model.DayType;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeekDistributionInvariantValidatorTest {

    @Test
    void shouldAcceptValidWeekDistribution() {
        WeekDistribution week = WeekDistribution.builder()
                .trainingDays(3)
                .restDays(2)
                .days(List.of(
                        DayDistribution.builder().dayType(DayType.TRAINING).build(),
                        DayDistribution.builder().dayType(DayType.TRAINING).build(),
                        DayDistribution.builder().dayType(DayType.TRAINING).build(),
                        DayDistribution.builder().dayType(DayType.REST).build(),
                        DayDistribution.builder().dayType(DayType.REST).build()
                ))
                .build();

        assertDoesNotThrow(() -> WeekDistributionInvariantValidator.validate(week));
    }

    @Test
    void shouldFailWhenDaysAreMissing() {
        WeekDistribution week = WeekDistribution.builder()
                .trainingDays(3)
                .restDays(2)
                .days(List.of())
                .build();

        assertThrows(IllegalStateException.class,
                () -> WeekDistributionInvariantValidator.validate(week));
    }

    @Test
    void shouldFailWhenTrainingAndRestDaysDoNotMatchDaysSize() {
        WeekDistribution week = WeekDistribution.builder()
                .trainingDays(4)
                .restDays(2)
                .days(List.of(
                        DayDistribution.builder().build(),
                        DayDistribution.builder().build()
                ))
                .build();

        assertThrows(IllegalStateException.class,
                () -> WeekDistributionInvariantValidator.validate(week));
    }
}
