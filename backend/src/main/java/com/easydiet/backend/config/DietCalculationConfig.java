package com.easydiet.backend.config;

import com.easydiet.backend.domain.diet.calculation.DefaultTdeeCalculator;
import com.easydiet.backend.domain.diet.calculation.TdeeCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DietCalculationConfig {

    @Bean
    public TdeeCalculator tdeeCalculator() {
        return new DefaultTdeeCalculator();
    }
}
