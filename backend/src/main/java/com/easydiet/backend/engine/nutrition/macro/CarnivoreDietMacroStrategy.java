package com.easydiet.backend.engine.nutrition.macro;

import com.easydiet.backend.domain.diet.enums.DietCode;

public class CarnivoreDietMacroStrategy implements DietMacroStrategy {

    private static final double FAT_PERCENT = 0.35;
    private static final int MAX_CARBS_GRAMS = 10;

    private static final int KCAL_PER_GRAM_FAT = 9;
    private static final int KCAL_PER_GRAM_CARBS = 4;
    private static final int KCAL_PER_GRAM_PROTEIN = 4;

    @Override
    public DietCode supports() {
        return DietCode.CARNIVORE;
    }

    @Override
    public int calculateFatGrams(int calories) {
        return (int) Math.round((calories * FAT_PERCENT) / KCAL_PER_GRAM_FAT);
    }

    @Override
    public int calculateCarbsGrams(
            int calories,
            int proteinGrams,
            int fatGrams
    ) {

        int caloriesFromProtein = proteinGrams * KCAL_PER_GRAM_PROTEIN;
        int caloriesFromFat = fatGrams * KCAL_PER_GRAM_FAT;

        int remainingCalories = calories - caloriesFromProtein - caloriesFromFat;
        int carbs = Math.max(0, remainingCalories / KCAL_PER_GRAM_CARBS);

        return Math.min(carbs, MAX_CARBS_GRAMS);
    }
}
