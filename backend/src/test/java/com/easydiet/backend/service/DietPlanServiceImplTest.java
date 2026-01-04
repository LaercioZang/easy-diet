package com.easydiet.backend.service;

import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.domain.diet.command.DietPlanGenerateCommand;
import com.easydiet.backend.domain.user.BodyProfile;
import com.easydiet.backend.domain.user.enums.ActivityLevel;
import com.easydiet.backend.domain.user.enums.Gender;
import com.easydiet.backend.engine.orchestrator.DietPlanOrchestrator;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import com.easydiet.backend.domain.diet.calculation.TdeeCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DietPlanServiceImplTest {

    @Mock
    private DietPlanOrchestrator dietPlanOrchestrator;

    @Mock
    private TdeeCalculator tdeeCalculator;

    @InjectMocks
    private DietPlanServiceImpl service;

    @Test
    void shouldGenerateDietPlanUsingCalculatedTdeeAndEngine() {

        BodyProfile profile = new BodyProfile(
                new BigDecimal("75"),
                178,
                24,
                Gender.MALE
        );

        DietPlanGenerateCommand command = new DietPlanGenerateCommand(
                null, // goal não é usado diretamente no service
                null, // dietType idem
                profile,
                ActivityLevel.MODERATE,
                5,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY)
        );

        when(tdeeCalculator.calculate(profile, ActivityLevel.MODERATE))
                .thenReturn(2600);

        WeekDistribution weekDistribution = mock(WeekDistribution.class);

        when(dietPlanOrchestrator.generateWeeklyPlan(
                75.0,
                2600,
                null,
                null,
                5,
                2
        )).thenReturn(weekDistribution);

        DietPlan result = service.generate(command);

        assertThat(result).isNotNull();
        assertThat(result.getTdee()).isEqualTo(2600);
        assertThat(result.getWeekDistribution()).isSameAs(weekDistribution);

        verify(tdeeCalculator).calculate(profile, ActivityLevel.MODERATE);
        verify(dietPlanOrchestrator).generateWeeklyPlan(
                75.0,
                2600,
                null,
                null,
                5,
                2
        );
    }
}
