package com.easydiet.backend.persistence.meal;

import com.easydiet.backend.persistence.food.FoodEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "food_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItemEntity {

    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Meal ao qual esse food item pertence
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meal_id", nullable = false)
    private MealEntity meal;

    /**
     * Referência ao alimento base (não é snapshot)
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "food_id", nullable = false)
    private FoodEntity food;

    /**
     * Quantidade consumida (em gramas, unidades, etc — regra fica no domínio)
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    /**
     * Snapshot nutricional (congelado no momento da criação)
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal calories;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal protein;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal carbs;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fat;
}
