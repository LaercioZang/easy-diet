package com.easydiet.backend.controller.v1.meal;

import com.easydiet.backend.config.security.SecurityUtils;
import com.easydiet.backend.domain.meal.command.MealCreateCommand;
import com.easydiet.backend.domain.meal.command.MealUpdateCommand;
import com.easydiet.backend.mapper.MealMapper;
import com.easydiet.backend.dto.meal.MealCreateRequest;
import com.easydiet.backend.dto.meal.MealResponse;
import com.easydiet.backend.dto.meal.MealUpdateRequest;
import com.easydiet.backend.persistence.meal.MealEntity;
import com.easydiet.backend.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meals")
@RequiredArgsConstructor
public class MealControllerV1 {

    private final MealService mealService;

    @PostMapping
    public MealResponse create(
            @RequestBody @Valid MealCreateRequest request
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();

        MealCreateCommand command = MealMapper.toCreateCommand(request);

        MealEntity meal = mealService.create(userId, command);

        return MealMapper.toResponse(meal);
    }

    /**
     * LIST Meals by Day (ACTIVE DietPlan)
     */
    @GetMapping
    public List<MealResponse> listByDay(
            @RequestParam DayOfWeek day
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();

        return mealService.findByDay(userId, day)
                .stream()
                .map(MealMapper::toResponse)
                .toList();
    }

    /**
     * UPDATE Meal
     */
    @PutMapping("/{id}")
    public MealResponse update(
            @PathVariable UUID id,
            @RequestBody @Valid MealUpdateRequest request
    ) {
        UUID userId = SecurityUtils.getCurrentUserId();

        MealUpdateCommand command = MealMapper.toUpdateCommand(request);

        MealEntity updated = mealService.update(id, userId, command);

        return MealMapper.toResponse(updated);
    }

    /**
     * DELETE Meal
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        mealService.delete(id, userId);
    }
}
