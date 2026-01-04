package com.easydiet.backend.dto;

import com.easydiet.backend.domain.diet.enums.DietCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietTypeRequest {

    @NotNull(message = "Diet code is required")
    private DietCode code;

    @NotBlank(message = "Diet name is required")
    @Size(max = 100, message = "Diet name must not exceed 100 characters")
    private String name;

    @Builder.Default
    private boolean active = true;
}
