package com.thekey.stylekeyserver.category.service;

import static com.thekey.stylekeyserver.common.exception.ErrorCode.CATEGORY_NOT_FOUND;

import com.thekey.stylekeyserver.category.entity.Category;
import com.thekey.stylekeyserver.category.repository.CategoryRepository;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND.getMessage()));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
