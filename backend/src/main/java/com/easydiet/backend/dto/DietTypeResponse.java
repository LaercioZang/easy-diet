package com.easydiet.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietTypeResponse {
    private UUID id;
    private String code;
    private String name;
    private boolean active;
}
