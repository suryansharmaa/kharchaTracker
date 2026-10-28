package com.Project.kharchaTracker.service;

import com.Project.kharchaTracker.dto.CategoryDto;
import com.Project.kharchaTracker.dto.CreateCategoryDto;
import com.Project.kharchaTracker.entities.Category;
import com.Project.kharchaTracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryDto createCategory(CreateCategoryDto createCategoryDto) {
        Category category = new Category();
        category.setCategoryName(createCategoryDto.getCategoryName());

        Category savedCategory = categoryRepository.save(category);
        return new CategoryDto(savedCategory.getCategoryId(), savedCategory.getCategoryName());
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> categoryDtoList = new ArrayList<>();

        List<Category> categoryList = categoryRepository.findAll();
        for(Category category : categoryList) {
            categoryDtoList.add(new CategoryDto(category.getCategoryId(), category.getCategoryName()));
        }

        return categoryDtoList;
    }
}
