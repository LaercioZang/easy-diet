package com.easydiet.backend.engine.week;

import com.easydiet.backend.domain.plan.DayPlan;
import com.easydiet.backend.domain.plan.WeekPlan;
import com.easydiet.backend.dto.WeekPlanRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DefaultWeekDietEngineTest {

    private DefaultWeekDietEngine engine;

    @BeforeEach
    void setUp() {
        engine = new DefaultWeekDietEngine();
    }

    @Test
    void shouldGenerateSevenDays() {
        WeekPlanRequest request = baseRequest();

        WeekPlan weekPlan = engine.generate(request);

        assertNotNull(weekPlan);
        assertEquals(7, weekPlan.getDays().size());
    }

    @Test
    void shouldMarkTrainingAndRestDaysCorrectly() {
        WeekPlanRequest request = baseRequest();

        WeekPlan weekPlan = engine.generate(request);

        for (DayPlan day : weekPlan.getDays()) {
            if (request.getTrainingDays().contains(day.getDayOfWeek().name())) {
                assertTrue(day.isTrainingDay(), "Expected training day: " + day.getDayOfWeek());
            } else {
                assertFalse(day.isTrainingDay(), "Expected rest day: " + day.getDayOfWeek());
            }
        }
    }

    private WeekPlanRequest baseRequest() {
        return WeekPlanRequest.builder()
                .calories(2600)
                .mealsPerDay(4)
                .trainingDays(Set.of(
                        DayOfWeek.MONDAY.name(),
                        DayOfWeek.TUESDAY.name(),
                        DayOfWeek.THURSDAY.name(),
                        DayOfWeek.FRIDAY.name(),
                        DayOfWeek.SATURDAY.name()
                ))
                .build();
    }
}
