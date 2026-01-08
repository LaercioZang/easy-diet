package com.easydiet.backend.service.diet;

import com.easydiet.backend.domain.diet.DietPlan;
import com.easydiet.backend.domain.diet.calculation.TdeeCalculator;
import com.easydiet.backend.domain.diet.command.DietPlanGenerateCommand;
import com.easydiet.backend.dto.diet.DietPlanTotalsResponse;
import com.easydiet.backend.engine.orchestrator.DietPlanOrchestrator;
import com.easydiet.backend.engine.week.model.WeekDistribution;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.mapper.DietPlanPersistenceMapper;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.persistence.diet.DietPlanRepository;
import com.easydiet.backend.persistence.diet.DietPlanStatus;
import com.easydiet.backend.persistence.meal.MealEntity;
import com.easydiet.backend.persistence.meal.MealRepository;
import com.easydiet.backend.persistence.user.UserEntity;
import com.easydiet.backend.persistence.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Application Service responsável por casos de uso relacionados a DietPlan.
 *
 * Regras deste service:
 * - Valida apenas parâmetros de entrada (null, range, permissões).
 * - NÃO executa cálculos nutricionais.
 * - NÃO chama engines diretamente.
 * - Toda lógica de distribuição/calculo deve ser delegada ao DietPlanOrchestrator.
 * - Este service coordena o fluxo e controla a transação.
 */
@RequiredArgsConstructor
@Service
public class DietPlanServiceImpl implements DietPlanService {

    private final DietPlanOrchestrator dietPlanOrchestrator;
    private final TdeeCalculator tdeeCalculator;
    private final DietPlanRepository dietPlanRepository;
    private final UserRepository userRepository;

    @Override
    public DietPlan generate(DietPlanGenerateCommand command, UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DomainException(
                        ErrorCode.RESOURCE_NOT_FOUND
                ));

        int tdee = tdeeCalculator.calculate(
                command.bodyProfile(),
                command.activityLevel()
        );

        WeekDistribution weekDistribution =
                dietPlanOrchestrator.generateWeeklyPlan(
                        command.bodyProfile().weightKg().doubleValue(),
                        tdee,
                        command.goal(),
                        command.dietType(),
                        command.mealsPerDay(),
                        command.trainingDays().size()
                );

        DietPlan plan = new DietPlan(tdee, weekDistribution);

        DietPlanEntity entity = DietPlanPersistenceMapper.toEntity(plan);
        entity.setUser(user);

        dietPlanRepository.save(entity);

        return plan;
    }

    @Override
    public List<DietPlanEntity> findAllByUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new DomainException(
                    ErrorCode.RESOURCE_NOT_FOUND
            );
        }

        return dietPlanRepository.findAllByUser_Id(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DietPlanEntity> findAllByUser(UUID userId, Pageable pageable) {

        if (!userRepository.existsById(userId)) {
            throw new DomainException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        return dietPlanRepository
                .findAllByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    public DietPlanEntity findById(UUID id) {
        return dietPlanRepository.findById(id)
                .orElseThrow(() -> new DomainException(
                        ErrorCode.RESOURCE_NOT_FOUND
                ));
    }

    @Override
    public List<DietPlanEntity> findAll() {
        return dietPlanRepository.findAll();
    }

    @Transactional
    public DietPlanEntity activate(UUID planId, UUID userId) {

        DietPlanEntity plan = dietPlanRepository.findById(planId)
                .orElseThrow(() -> new DomainException(ErrorCode.RESOURCE_NOT_FOUND));

        if (!plan.getUser().getId().equals(userId)) {
            throw new DomainException(ErrorCode.INVALID_ARGUMENT);
        }

        // Arquiva plano ativo atual (se existir)
        dietPlanRepository.findByUserIdAndStatus(userId, DietPlanStatus.ACTIVE)
                .ifPresent(active -> {
                    active.setStatus(DietPlanStatus.ARCHIVED);
                });

        // Ativa o novo
        plan.setStatus(DietPlanStatus.ACTIVE);
        plan.setActivatedAt(Instant.now());

        return plan;
    }


    @Override
    @Transactional()
    public DietPlanEntity findActive(UUID userId) {

        return dietPlanRepository
                .findByUserIdAndStatus(userId, DietPlanStatus.ACTIVE)
                .orElseThrow(() ->
                        new DomainException(ErrorCode.RESOURCE_NOT_FOUND)
                );
    }
}

