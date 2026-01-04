package com.easydiet.backend.domain.diet;

import com.easydiet.backend.engine.week.model.WeekDistribution;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DietPlanTest {

    @Test
    void shouldCreateDietPlanWithWeekDistribution() {
        WeekDistribution weekDistribution = mock(WeekDistribution.class);

        DietPlan plan = new DietPlan(2500, weekDistribution);

        assertThat(plan.getTdee()).isEqualTo(2500);
        assertThat(plan.getWeekDistribution()).isSameAs(weekDistribution);
    }
}
