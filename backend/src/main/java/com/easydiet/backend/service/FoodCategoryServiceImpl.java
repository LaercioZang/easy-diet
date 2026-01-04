package com.easydiet.backend.service;

import com.easydiet.backend.domain.food.FoodCategory;
import com.easydiet.backend.dto.FoodCategoryRequest;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.mapper.FoodCategoryMapper;
import com.easydiet.backend.persistence.food.FoodCategoryEntity;
import com.easydiet.backend.persistence.food.FoodCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final FoodCategoryRepository repository;

    @Override
    public List<FoodCategory> findAll() {
        return repository.findAll().stream()
                .map(FoodCategoryMapper::toDomain)
                .toList();
    }

    @Override
    public List<FoodCategory> findAllActive() {
        return repository.findByActiveTrue().stream()
                .map(FoodCategoryMapper::toDomain)
                .toList();
    }

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

    @Override
    @Transactional
    public FoodCategory create(FoodCategoryRequest request) {
        FoodCategoryEntity entity = FoodCategoryEntity.builder()
                .id(UUID.randomUUID())
                .code(request.getCode())
                .name(request.getName())
                .active(request.isActive())
                .build();
        
        return FoodCategoryMapper.toDomain(repository.save(entity));
    }

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

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new DomainException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
