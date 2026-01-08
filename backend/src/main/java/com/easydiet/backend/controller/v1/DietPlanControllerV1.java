package com.easydiet.backend.controller.v1;

import com.easydiet.backend.config.security.SecurityUtils;
import com.easydiet.backend.dto.DietPlanGenerateRequest;
import com.easydiet.backend.dto.DietPlanGenerateResponse;
import com.easydiet.backend.dto.diet.DietPlanSnapshotResponse;
import com.easydiet.backend.mapper.DietPlanMapper;
import com.easydiet.backend.mapper.DietPlanSnapshotMapper;
import com.easydiet.backend.service.DietPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/diet-plans")
@RequiredArgsConstructor
public class DietPlanControllerV1 {

    private final DietPlanService dietPlanService;

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.OK)
    public DietPlanGenerateResponse generate(
            @RequestBody @Valid DietPlanGenerateRequest request
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();

        return DietPlanMapper.toResponse(
                dietPlanService.generate(
                        DietPlanMapper.toCommand(request),
                        userId
                )
        );
    }

    @GetMapping("/{id}")
    public DietPlanSnapshotResponse findById(@PathVariable UUID id) {
        return DietPlanSnapshotMapper.toResponse(
                dietPlanService.findById(id)
        );
    }

    @GetMapping("/find-all")
    public List<DietPlanSnapshotResponse> findAll() {
        return dietPlanService.findAll()
                .stream()
                .map(DietPlanSnapshotMapper::toResponse)
                .toList();
    }

    @GetMapping("/user")
    public List<DietPlanSnapshotResponse> findAllByUser(
    ) {

        UUID userId = SecurityUtils.getCurrentUserId();

        return dietPlanService.findAllByUser(userId)
                .stream()
                .map(DietPlanSnapshotMapper::toResponse)
                .toList();
    }

    @PostMapping("/{id}/activate")
    public DietPlanSnapshotResponse activate(@PathVariable UUID id) {

        UUID userId = SecurityUtils.getCurrentUserId();

        return DietPlanSnapshotMapper.toResponse(
                dietPlanService.activate(id, userId)
        );
    }

    @GetMapping
    public List<DietPlanSnapshotResponse> list() {

        UUID userId = SecurityUtils.getCurrentUserId();

        return dietPlanService.findAllByUser(userId)
                .stream()
                .map(DietPlanSnapshotMapper::toResponse)
                .toList();
    }
}
