package com.easydiet.backend.controller.v1;

import com.easydiet.backend.domain.diet.DietType;
import com.easydiet.backend.dto.DietTypeResponse;
import com.easydiet.backend.mapper.DietTypeMapper;
import com.easydiet.backend.service.diet.DietTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diet-types")
@RequiredArgsConstructor
public class DietTypeControllerV1 {

    private final DietTypeService dietTypeService;

    @GetMapping
    public List<DietTypeResponse> findAll(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String search
    ) {

        List<DietType> dietTypes =
                dietTypeService.findAll(active, search);

        return dietTypes.stream()
                .map(DietTypeMapper::toResponse)
                .toList();
    }
}
