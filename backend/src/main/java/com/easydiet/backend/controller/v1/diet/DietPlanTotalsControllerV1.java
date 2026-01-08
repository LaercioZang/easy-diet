package com.easydiet.backend.controller.v1.diet;

import com.easydiet.backend.config.security.SecurityUtils;
import com.easydiet.backend.dto.diet.DietPlanTotalsResponse;
import com.easydiet.backend.service.diet.DietPlanTotalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/diet-plans")
@RequiredArgsConstructor
public class DietPlanTotalsControllerV1 {

    private final DietPlanTotalsService dietPlanTotalsService;

    @GetMapping("/active/totals")
    public DietPlanTotalsResponse getTotals() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return dietPlanTotalsService.calculateForActivePlan(userId);
    }
}
