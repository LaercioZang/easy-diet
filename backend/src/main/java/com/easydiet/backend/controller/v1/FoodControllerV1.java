package com.easydiet.backend.controller.v1;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.dto.FoodResponse;
import com.easydiet.backend.mapper.FoodMapper;
import com.easydiet.backend.service.food.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodControllerV1 {

    private final FoodService foodService;

    @GetMapping
    public Page<FoodResponse> findAll(Pageable pageable
    ) {

        List<Food> foods = foodService.findAll(active, search, category);

        return foods.stream()
                .map(FoodMapper::toResponse)
                .toList();
    }
}
