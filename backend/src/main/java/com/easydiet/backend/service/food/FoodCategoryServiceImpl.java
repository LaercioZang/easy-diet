package com.easydiet.backend.service.food;

import com.easydiet.backend.domain.food.FoodCategory;
import com.easydiet.backend.dto.FoodCategoryRequest;
import com.easydiet.backend.dto.FoodCategoryResponse;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.mapper.FoodCategoryMapper;
import com.easydiet.backend.persistence.food.FoodCategoryEntity;
import com.easydiet.backend.persistence.food.FoodCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
 
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final FoodCategoryRepository repository;

    
    @Cacheable("foodCategoriesAll")
    @Override
    public List<FoodCategory> findAll() {
        return repository.findAll().stream()
                .map(FoodCategoryMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FoodCategoryResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(FoodCategoryMapper::toDomain)
                .map(FoodCategoryMapper::toResponse);
    }

    @Cacheable("foodCategoriesActive")
    @Override
    public List<FoodCategory> findAllActive() {
        return repository.findByActiveTrue().stream()
                .map(FoodCategoryMapper::toDomain)
                .toList();
    }

    @Cacheable(
            value = "foodCategoriesFiltered",
            key = "T(java.util.Objects).hash(#active, #search)"
    )
    @Override
    public List<FoodCategory> findAll(Boolean active, String search) {

        if (active == null && search == null) {
            return repository.findAll()
                    .stream()
                    .map(FoodCategoryMapper::toDomain)
                    .toList();
        }

        if (active != null && search == null) {
            return (active
                    ? repository.findByActiveTrue()
                    : repository.findByActiveFalse())
                    .stream()
                    .map(FoodCategoryMapper::toDomain)
                    .toList();
        }

        if (active == null && search != null) {
            return repository.findByNameContainingIgnoreCase(search)
                    .stream()
                    .map(FoodCategoryMapper::toDomain)
                    .toList();
        }

        throw new UnsupportedOperationException(
                "Combination of filters not supported yet"
        );
    }

    @Override
    public FoodCategory findById(UUID id) {
        return repository.findById(id)
                .map(FoodCategoryMapper::toDomain)
                .orElseThrow(() -> new DomainException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    @CacheEvict(
            value = {
                    "foodCategoriesAll",
                    "foodCategoriesActive",
                    "foodCategoriesFiltered"
            },
            allEntries = true
    )
    @Transactional
    @Override
    public FoodCategory create(FoodCategoryRequest request) {
        FoodCategoryEntity entity = FoodCategoryEntity.builder()
                .id(UUID.randomUUID())
                .code(request.getCode())
                .name(request.getName())
                .active(request.isActive())
                .build();
        
        return FoodCategoryMapper.toDomain(repository.save(entity));
    }

    @CacheEvict(
            value = {
                    "foodCategoriesAll",
                    "foodCategoriesActive",
                    "foodCategoriesFiltered"
            },
            allEntries = true
    )
    @Override
    @Transactional
    public FoodCategory update(UUID id, FoodCategoryRequest request) {
        FoodCategoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorCode.RESOURCE_NOT_FOUND));
        
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setActive(request.isActive());
        
        return FoodCategoryMapper.toDomain(repository.save(entity));
    }

    @CacheEvict(
            value = {
                    "foodCategoriesAll",
                    "foodCategoriesActive",
                    "foodCategoriesFiltered"
            },
            allEntries = true
    )
    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new DomainException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
