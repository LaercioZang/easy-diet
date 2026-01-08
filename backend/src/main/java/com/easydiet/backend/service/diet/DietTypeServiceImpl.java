package com.easydiet.backend.service.diet;

import com.easydiet.backend.domain.diet.DietType;
import com.easydiet.backend.dto.DietTypeRequest;
import com.easydiet.backend.exception.DomainException;
import com.easydiet.backend.exception.ErrorCode;
import com.easydiet.backend.mapper.DietTypeMapper;
import com.easydiet.backend.persistence.diet.DietTypeEntity;
import com.easydiet.backend.persistence.diet.DietTypeRepository;
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
public class DietTypeServiceImpl implements DietTypeService {

    private final DietTypeRepository repository;

    @Cacheable("dietTypesAll")
    @Override
    public List<DietType> findAll() {
        return repository.findAll().stream()
                .map(DietTypeMapper::toDomain)
                .toList();
    }

    @Cacheable("dietTypesActive")
    @Override
    public List<DietType> findAllActive() {
        return repository.findByActiveTrue().stream()
                .map(DietTypeMapper::toDomain)
                .toList();
    }

    @Cacheable(
            value = "dietTypesFiltered",
            key = "T(java.util.Objects).hash(#active, #search)"
    )
    @Override
    public List<DietType> findAll(Boolean active, String search) {

        if (active == null && search == null) {
            return repository.findAll()
                    .stream()
                    .map(DietTypeMapper::toDomain)
                    .toList();
        }

        if (active != null && search == null) {
            return (active
                    ? repository.findByActiveTrue()
                    : repository.findByActiveFalse())
                    .stream()
                    .map(DietTypeMapper::toDomain)
                    .toList();
        }

        if (active == null && search != null) {
            return repository.findByNameContainingIgnoreCase(search)
                    .stream()
                    .map(DietTypeMapper::toDomain)
                    .toList();
        }

        throw new UnsupportedOperationException(
                "Combination of filters not supported yet"
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DietType> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(DietTypeMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DietType> findAll(Boolean active, Pageable pageable) {
        return active
                ? repository.findByActiveTrue(pageable).map(DietTypeMapper::toDomain)
                : repository.findByActiveFalse(pageable).map(DietTypeMapper::toDomain);
    }

    @Override
    public Page<DietType> findAll(Boolean active, String search, Pageable pageable) {
        return repository.findByActiveAndNameContainingIgnoreCase(active, search, pageable).map(DietTypeMapper::toDomain);
    }

    @Override
    public DietType findById(UUID id) {
        return repository.findById(id)
                .map(DietTypeMapper::toDomain)
                .orElseThrow(() -> new DomainException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    @CacheEvict(
            value = {
                    "dietTypesAll",
                    "dietTypesActive",
                    "dietTypesFiltered"
            },
            allEntries = true
    )
    @Override
    @Transactional
    public DietType create(DietTypeRequest request) {
        DietTypeEntity entity = DietTypeEntity.builder()
                .id(UUID.randomUUID())
                .code(request.getCode())
                .name(request.getName())
                .active(request.isActive())
                .build();
        
        return DietTypeMapper.toDomain(repository.save(entity));
    }

    @CacheEvict(
            value = {
                    "dietTypesAll",
                    "dietTypesActive",
                    "dietTypesFiltered"
            },
            allEntries = true
    )
    @Override
    @Transactional
    public DietType update(UUID id, DietTypeRequest request) {
        DietTypeEntity entity = repository.findById(id)
                .orElseThrow(() -> new DomainException(ErrorCode.RESOURCE_NOT_FOUND));
        
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setActive(request.isActive());
        
        return DietTypeMapper.toDomain(repository.save(entity));
    }

    @CacheEvict(
            value = {
                    "dietTypesAll",
                    "dietTypesActive",
                    "dietTypesFiltered"
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
