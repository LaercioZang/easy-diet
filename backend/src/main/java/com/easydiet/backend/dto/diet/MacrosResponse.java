package com.easydiet.backend.dto.diet;

public record MacrosResponse(
        Integer protein,
        Integer carbs,
        Integer fat
) {}
