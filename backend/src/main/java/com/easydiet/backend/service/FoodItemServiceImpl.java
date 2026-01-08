package com.easydiet.backend.service;

import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.persistence.diet.DietPlanEntity;
import com.easydiet.backend.persistence.diet.DietPlanStatus;
import com.easydiet.backend.persistence.food.FoodEntity;
import com.easydiet.backend.persistence.food.FoodRepository;
import com.easydiet.backend.persistence.meal.FoodItemEntity;
import com.easydiet.backend.persistence.meal.FoodItemRepository;
import com.easydiet.backend.persistence.meal.MealEntity;
import com.easydiet.backend.persistence.meal.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodItemServiceImpl implements FoodItemService {

    private final MealRepository mealRepository;
    private final FoodRepository foodRepository;
    private final FoodItemRepository foodItemRepository;

    @Override
    public FoodItemEntity create(
            UUID userId,
            UUID mealId,
            UUID foodId,
            double quantity
    ) {

        MealEntity meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new DomainException(
                        ErrorCode.RESOURCE_NOT_FOUND
                ));

        DietPlanEntity plan = meal.getDietPlan();

        if (!plan.getUser().getId().equals(userId)) {
            throw new DomainException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        if (plan.getStatus() != DietPlanStatus.ACTIVE) {
            throw new DomainException(ErrorCode.INVALID_ARGUMENT);
        }

        FoodEntity food = foodRepository.findById(foodId)
                .orElseThrow(() -> new DomainException(
                        ErrorCode.RESOURCE_NOT_FOUND
                ));

        BigDecimal qty = BigDecimal.valueOf(quantity);

        BigDecimal factor = qty.divide(BigDecimal.valueOf(100));

        BigDecimal calories = food.getCalories().multiply(factor);
        BigDecimal protein = food.getProtein().multiply(factor);
        BigDecimal carbs   = food.getCarbs().multiply(factor);
        BigDecimal fat     = food.getFat().multiply(factor);

        FoodItemEntity item = FoodItemEntity.builder()
                .meal(meal)
                .food(food)
                .quantity(qty)
                .calories(calories)
                .protein(protein)
                .carbs(carbs)
                .fat(fat)
                .build();

        FoodItemEntity saved = foodItemRepository.save(item);

        if (meal.getFoodItems() == null) {
            meal.setFoodItems(new ArrayList<>());
        }

         meal.getFoodItems().add(saved);

        recalculateMealTotals(meal);

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FoodItemEntity> findByMeal(
            UUID userId,
            UUID mealId
    ) {

        MealEntity meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new DomainException(
                        ErrorCode.RESOURCE_NOT_FOUND
                ));

        if (!meal.getDietPlan().getUser().getId().equals(userId)) {
            throw new DomainException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        return foodItemRepository.findAllByMealId(mealId);
    }

    @Override
    @Transactional
    public void delete(UUID userId, UUID foodItemId) {

        FoodItemEntity item = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new DomainException(ErrorCode.RESOURCE_NOT_FOUND));

        MealEntity meal = item.getMeal();

        if (!meal.getDietPlan().getUser().getId().equals(userId)) {
            throw new DomainException(ErrorCode.INVALID_ARGUMENT);
        }

        // 1️⃣ Remove da coleção da Meal
        meal.getFoodItems().remove(item);

        // 2️⃣ Remove do banco
        foodItemRepository.delete(item);

        // 3️⃣ Recalcula os totais da Meal
        recalculateMealTotals(meal);
    }

    private void recalculateMealTotals(MealEntity meal) {

        BigDecimal calories = BigDecimal.ZERO;
        BigDecimal protein  = BigDecimal.ZERO;
        BigDecimal carbs    = BigDecimal.ZERO;
        BigDecimal fat      = BigDecimal.ZERO;

        for (FoodItemEntity item : meal.getFoodItems()) {
            calories = calories.add(item.getCalories());
            protein  = protein.add(item.getProtein());
            carbs    = carbs.add(item.getCarbs());
            fat      = fat.add(item.getFat());
        }

        meal.setTotalCalories(calories);
        meal.setTotalProtein(protein);
        meal.setTotalCarbs(carbs);
        meal.setTotalFat(fat);
    }
}
