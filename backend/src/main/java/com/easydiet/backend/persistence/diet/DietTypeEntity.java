package com.easydiet.backend.persistence.diet;

import com.easydiet.backend.domain.diet.enums.DietCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "diet_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietTypeEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true, length = 50)
    private DietCode code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "active", nullable = false)
    private boolean active;
}
