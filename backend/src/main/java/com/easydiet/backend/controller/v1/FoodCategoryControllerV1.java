package com.easydiet.backend.controller.v1;

import com.easydiet.backend.domain.food.FoodCategory;
import com.easydiet.backend.dto.FoodCategoryResponse;
import com.easydiet.backend.mapper.FoodCategoryMapper;
import com.easydiet.backend.service.food.FoodCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/food-categories")
@RequiredArgsConstructor
public class FoodCategoryControllerV1 {

    private final FoodCategoryService foodCategoryService;

    @GetMapping
    public List<FoodCategoryResponse> findAll(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String search
    ) {

        List<FoodCategory> categories =
                foodCategoryService.findAll(active, search);

        return categories.stream()
                .map(FoodCategoryMapper::toResponse)
                .toList();
    }
}
