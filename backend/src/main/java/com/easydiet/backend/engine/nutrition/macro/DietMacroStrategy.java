package com.easydiet.backend.engine.nutrition.macro;

import com.easydiet.backend.domain.diet.enums.DietCode;

public interface DietMacroStrategy {

    DietCode supports();

    int calculateFatGrams(int calories);

    int calculateCarbsGrams(
        int calories,
        int proteinGrams,
        int fatGrams
    );
}
