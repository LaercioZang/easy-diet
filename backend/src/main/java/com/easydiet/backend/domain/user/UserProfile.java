package com.easydiet.backend.domain.user;

import com.easydiet.backend.domain.user.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    private String id;
    private Gender gender;
    private int age;
    private int heightCm;
    private double weightKg;
}
