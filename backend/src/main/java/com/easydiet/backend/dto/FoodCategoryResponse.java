package com.easydiet.backend.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FoodCategoryResponse {

    private UUID id;
    private String code;
    private String name;
    private boolean active;
}
