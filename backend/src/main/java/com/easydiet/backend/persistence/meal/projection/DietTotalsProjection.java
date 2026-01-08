package com.easydiet.backend.persistence.meal.projection;

public interface DietTotalsProjection {

    Integer getCalories();
    Integer getProtein();
    Integer getCarbs();
    Integer getFat();
}
