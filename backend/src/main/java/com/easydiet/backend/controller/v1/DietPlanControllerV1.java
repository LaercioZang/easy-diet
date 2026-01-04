package com.easydiet.backend.controller.v1;

import com.easydiet.backend.dto.DietPlanGenerateRequest;
import com.easydiet.backend.dto.DietPlanGenerateResponse;
import com.easydiet.backend.mapper.DietPlanMapper;
import com.easydiet.backend.service.DietPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return DietPlanMapper.toResponse(
                dietPlanService.generate(
                        DietPlanMapper.toCommand(request)
                )
        );
    }
}
