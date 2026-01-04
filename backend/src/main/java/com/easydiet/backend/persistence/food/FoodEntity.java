package com.easydiet.backend.persistence.food;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private FoodCategoryEntity foodCategory;

    @Column(nullable = false)
    private Integer calories;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal protein;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal carbs;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal fat;

    @Column(nullable = false)
    private boolean active;
}
