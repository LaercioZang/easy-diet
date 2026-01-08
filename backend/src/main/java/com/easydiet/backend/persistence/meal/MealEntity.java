package com.easydiet.backend.persistence.meal;

import com.easydiet.backend.persistence.diet.DietPlanEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "meal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "diet_plan_id", nullable = false)
    private DietPlanEntity dietPlan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer mealOrder;

    /* ====== TOTALS (AGGREGATE STATE) ====== */

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCalories;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalProtein;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCarbs;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalFat;

    /* ====== RELATIONSHIPS ====== */

    @OneToMany(
            mappedBy = "meal",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FoodItemEntity> foodItems = new ArrayList<>();

    /* ====== AUDIT ====== */

    @Column(nullable = false)
    private Instant createdAt;
}