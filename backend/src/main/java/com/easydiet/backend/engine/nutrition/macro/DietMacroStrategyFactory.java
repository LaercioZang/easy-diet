package com.easydiet.backend.engine.nutrition.macro;

import com.easydiet.backend.domain.diet.enums.DietCode;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;

import java.util.List;

public class DietMacroStrategyFactory {

    private final List<DietMacroStrategy> strategies = List.of(
            new NormalDietMacroStrategy(),
            new KetoDietMacroStrategy(),
            new LowCarbDietMacroStrategy(),
            new SelvaDietMacroStrategy(),
            new VegetarianDietMacroStrategy(),
            new VeganDietMacroStrategy(),
            new CarnivoreDietMacroStrategy()
    );

    public DietMacroStrategy getStrategy(DietCode dietCode) {

        if (dietCode == null) {
            throw new DomainException(ErrorCode.NULL_VALUE);
        }

        return strategies.stream()
                .filter(s -> s.supports() == dietCode)
                .findFirst()
                .orElseThrow(() ->
                        new DomainException(ErrorCode.UNSUPPORTED_DIET)
                );
    }
}
