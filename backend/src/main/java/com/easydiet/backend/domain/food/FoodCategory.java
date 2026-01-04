package com.easydiet.backend.domain.food;

import com.easydiet.backend.domain.food.enums.Category;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Builder
@Getter
public class FoodCategory {
    private UUID id;
    private Category code;
    private String name;
    private boolean active;
}
