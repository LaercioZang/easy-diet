package com.easydiet.backend.controller;

import com.easydiet.backend.domain.food.enums.Category;
import com.easydiet.backend.dto.FoodResponse;
import com.easydiet.backend.mapper.FoodResponseMapper;
import com.easydiet.backend.service.food.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public List<FoodResponse> findAllActive() {
        return foodService.findAllActive()
            .stream()
            .map(FoodResponseMapper::toResponse)
            .toList();
    }

    @GetMapping("/category/{code}")
    public List<FoodResponse> findByCategory(@PathVariable String code) {
        Category category = Category.valueOf(code.toUpperCase());
        return foodService.findActiveByCategory(category)
                .stream()
                .map(FoodResponseMapper::toResponse)
                .toList();
    }
}
