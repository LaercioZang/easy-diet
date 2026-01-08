package com.easydiet.backend.service.food;

import com.easydiet.backend.domain.food.Food;
import com.easydiet.backend.domain.food.enums.Category;
import com.easydiet.backend.mapper.FoodMapper;
import com.easydiet.backend.persistence.food.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    
    @Override
    public List<Food> findAllActive() {
        return foodRepository.findByActiveTrue()
                .stream()
                .map(FoodMapper::toDomain)
                .toList();
    }


    @Override
    public List<Food> findActiveByCategory(Category category) {
        return foodRepository
                .findByFoodCategory_CodeAndActiveTrue(category)
                .stream()
                .map(FoodMapper::toDomain)
                .toList();
    }


    @Override
    public List<Food> findAll(Boolean active, String search, Category category) {

        if (active == null && search == null && category == null) {
            return foodRepository.findAll()
                    .stream()
                    .map(FoodMapper::toDomain)
                    .toList();
        }

        if (active != null && search == null && category == null) {
            return (active
                    ? foodRepository.findByActiveTrue()
                    : foodRepository.findByActiveFalse())
                    .stream()
                    .map(FoodMapper::toDomain)
                    .toList();
        }

        if (active == null && search != null && category == null) {
            return foodRepository.findByNameContainingIgnoreCase(search)
                    .stream()
                    .map(FoodMapper::toDomain)
                    .toList();
        }

        if (active == null && search == null && category != null) {
            return foodRepository.findByFoodCategory_Code(category)
                    .stream()
                    .map(FoodMapper::toDomain)
                    .toList();
        }

        throw new UnsupportedOperationException(
                "Combination of filters not supported yet"
        );
    }
}
