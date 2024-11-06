package com.videos_be.adults.category.service;

import com.videos_be.adults.category.dto.CreateCategoryDto;
import com.videos_be.adults.category.dto.UpdateCategoryDto;
import com.videos_be.adults.category.model.CategoryModel;
import com.videos_be.adults.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryModel addCategory(CreateCategoryDto createCategoryDto) {
        CategoryModel category = new CategoryModel(null, createCategoryDto.getName(), 0);
        try {
            return this.categoryRepository.save(category);
        } catch (Error error) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
        }

    }

    public Page<CategoryModel> getAllCategory(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }

    public CategoryModel getById(String id) {
        try {
            Optional<CategoryModel> category = this.categoryRepository.findById(id);
            if (category.isPresent()) return category.get();
        } catch (Error error) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
        }
        return null;
    }

    public CategoryModel updateCategory(UpdateCategoryDto updateCategoryDto, String id) {
        String name = updateCategoryDto.getName();
        Optional<CategoryModel> category = this.categoryRepository.findById(id);
        if (category.isPresent()) {
            CategoryModel currentCategory = category.get();
            currentCategory.setName(!Objects.equals(name, currentCategory.getName()) ? name : currentCategory.getName());

            try {
                return this.categoryRepository.save(currentCategory);

            } catch (Error error) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
            }

        }

        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not update the video", null);
    }

    public CategoryModel deleteCategory(String id) {
        Optional<CategoryModel> category = this.categoryRepository.findById(id);
        if (category.isPresent()) {
            try {
                this.categoryRepository.deleteById(id);
            } catch (Error error) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, error.getMessage(), null);
            }
            return category.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not delete the category", null);
    }

    public Boolean allCategoriesExistInDB(List<String> categories) {
        List<CategoryModel> dbCategories = this.categoryRepository.findAll();
        List<String> categoryNames = dbCategories.stream()
                .map(CategoryModel::getName)
                .toList();

        return new HashSet<>(categoryNames).containsAll(categories);
    }

}
