package com.easydiet.backend.domain.diet;

import com.easydiet.backend.domain.diet.enums.DietCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DietType {

    private String id;
    private DietCode code;     // NORMAL, KETO, SELVA, etc
    private String name;
    private boolean active;
}
