package com.easydiet.backend.domain.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Macros {

    private final int protein;
    private final int carbs;
    private final int fat;
}
