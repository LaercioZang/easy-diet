package com.easydiet.backend.controller.v2.diet;

import com.easydiet.backend.config.security.SecurityUtils;
import com.easydiet.backend.dto.diet.DietPlanResponse;
import com.easydiet.backend.mapper.diet.DietPlanResponseMapper;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.service.diet.DietPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/diet-plans")
@RequiredArgsConstructor
public class DietPlanControllerV2 {

    private final DietPlanService dietPlanService;

    @GetMapping("/active")
    public DietPlanResponse getActivePlan() {

        UUID userId = SecurityUtils.getCurrentUserId();

        DietPlanEntity activePlan =
                dietPlanService.findActive(userId);

        return DietPlanResponseMapper.toResponse(activePlan);
    }
}
