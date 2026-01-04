package com.easydiet.backend.controller;

import com.easydiet.backend.dto.DietTypeRequest;
import com.easydiet.backend.dto.DietTypeResponse;
import com.easydiet.backend.mapper.DietTypeMapper;
import com.easydiet.backend.service.DietTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/diet-types")
@RequiredArgsConstructor
public class DietTypeController {

    private final DietTypeService service;

    @GetMapping
    public List<DietTypeResponse> findAll(@RequestParam(required = false) Boolean active) {
        var dietTypes = (active != null && active) ? service.findAllActive() : service.findAll();
        return dietTypes.stream()
                .map(DietTypeMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public DietTypeResponse findById(@PathVariable UUID id) {
        return DietTypeMapper.toResponse(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DietTypeResponse create(@Valid @RequestBody DietTypeRequest request) {
        return DietTypeMapper.toResponse(service.create(request));
    }

    @PutMapping("/{id}")
    public DietTypeResponse update(@PathVariable UUID id, @Valid @RequestBody DietTypeRequest request) {
        return DietTypeMapper.toResponse(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
