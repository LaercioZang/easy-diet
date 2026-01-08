package com.easydiet.backend.controller;

import com.easydiet.backend.dto.FoodCategoryRequest;
import com.easydiet.backend.dto.FoodCategoryResponse;
import com.easydiet.backend.mapper.FoodCategoryMapper;
import com.easydiet.backend.service.food.FoodCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/food-categories")
@RequiredArgsConstructor
public class FoodCategoryController {

    private final FoodCategoryService service;

    @GetMapping
    public List<FoodCategoryResponse> findAll(@RequestParam(required = false) Boolean active) {
        var categories = (active != null && active) ? service.findAllActive() : service.findAll();
        return categories.stream()
                .map(FoodCategoryMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public FoodCategoryResponse findById(@PathVariable UUID id) {
        return FoodCategoryMapper.toResponse(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodCategoryResponse create(@Valid @RequestBody FoodCategoryRequest request) {
        return FoodCategoryMapper.toResponse(service.create(request));
    }

    @PutMapping("/{id}")
    public FoodCategoryResponse update(@PathVariable UUID id, @Valid @RequestBody FoodCategoryRequest request) {
        return FoodCategoryMapper.toResponse(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
