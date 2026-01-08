package com.easydiet.backend.controller.v1;

import com.easydiet.backend.config.security.SecurityUtils;
import com.easydiet.backend.dto.meal.FoodItemCreateRequest;
import com.easydiet.backend.dto.meal.FoodItemResponse;
import com.easydiet.backend.mapper.FoodItemMapper;
import com.easydiet.backend.persistence.meal.FoodItemEntity;
import com.easydiet.backend.service.FoodItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FoodItemControllerV1 {

    private final FoodItemService foodItemService;

    /**
     * CREATE FoodItem for a Meal (DietPlan ACTIVE)
     */
    @PostMapping("/meals/{mealId}/food-items")
    @ResponseStatus(HttpStatus.CREATED)
    public FoodItemResponse create(
            @PathVariable UUID mealId,
            @RequestBody @Valid FoodItemCreateRequest request
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();

        FoodItemEntity item = foodItemService.create(
                userId,
                mealId,
                request.foodId(),
                request.quantity().doubleValue()
        );

        return FoodItemMapper.toResponse(item);
    }

    /**
     * LIST FoodItems by Meal
     */
    @GetMapping("/meals/{mealId}/food-items")
    public List<FoodItemResponse> listByMeal(
            @PathVariable UUID mealId
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();

        return foodItemService.findByMeal(userId, mealId)
                .stream()
                .map(FoodItemMapper::toResponse)
                .toList();
    }

    /**
     * DELETE FoodItem
     */
    @DeleteMapping("/food-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();
        foodItemService.delete(userId, id);
    }
}
