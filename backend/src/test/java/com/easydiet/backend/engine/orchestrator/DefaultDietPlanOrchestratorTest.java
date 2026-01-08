package com.easydiet.backend.engine.orchestrator;

import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.domain.diet.enums.Goal;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DefaultDietPlanOrchestratorTest {

    private DefaultDietPlanOrchestrator orchestrator;

    @BeforeEach
    void setup() {
        orchestrator = new DefaultDietPlanOrchestrator();
    }

    @Test
    void shouldGenerateCompleteWeeklyPlan() {

        WeekDistribution plan = orchestrator.generateWeeklyPlan(
                70.0,
                2600,
                Goal.MAINTENANCE,
                DietCode.NORMAL,
                4,
                4
        );

        assertThat(plan.getDays()).hasSize(7);
        assertThat(plan.getTrainingDays()).isEqualTo(4);
        assertThat(plan.getRestDays()).isEqualTo(3);
    }

    @Test
    void shouldFailIfInvalidWeight() {
        assertThatThrownBy(() ->
                orchestrator.generateWeeklyPlan(
                        0,
                        2600,
                        Goal.BULK,
                        DietCode.NORMAL,
                        4,
                        3
                )
        )
                .isInstanceOf(DomainException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.OUT_OF_RANGE);
    }

    @Test
    void shouldFailIfNullGoal() {
        assertThatThrownBy(() ->
                orchestrator.generateWeeklyPlan(
                        70,
                        2600,
                        null,
                        DietCode.NORMAL,
                        4,
                        3
                )
        )
                .isInstanceOf(DomainException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NULL_VALUE);
    }

    @Test
    void shouldFailWhenDomainValidationFails() {
        assertThatThrownBy(() ->
                orchestrator.generateWeeklyPlan(
                        70,
                        2600,
                        Goal.MAINTENANCE,
                        DietCode.NORMAL,
                        0,   // mealsPerDay inválido → domínio
                        4
                )
        )
                .isInstanceOf(DomainException.class);
    }
}
