package com.easydiet.backend.controller.v1;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.domain.food.enums.Category;
import com.easydiet.backend.dto.FoodResponse;
import com.easydiet.backend.mapper.FoodMapper;
import com.easydiet.backend.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodControllerV1 {

    private final FoodService foodService;

    @GetMapping
    public List<FoodResponse> findAll(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Category category
    ) {

        List<Food> foods = foodService.findAll(active, search, category);

        return foods.stream()
                .map(FoodMapper::toResponse)
                .toList();
    }
}
