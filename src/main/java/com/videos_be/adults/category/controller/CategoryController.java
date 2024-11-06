package com.videos_be.adults.category.controller;
import com.videos_be.adults.category.dto.CategoriesDto;
import com.videos_be.adults.category.dto.CreateCategoryDto;
import com.videos_be.adults.category.dto.UpdateCategoryDto;
import com.videos_be.adults.category.service.CategoryService;
import com.videos_be.adults.handler.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/category")
public class CategoryController {

    @Autowired()
    private CategoryService categoryService;


    @GetMapping()
    public ResponseEntity<Object> getAllVideos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int sizePerPage) {

        Pageable pageable = (Pageable) PageRequest.of(page, sizePerPage);
        return ResponseHandler.generateResponse(
                "Category", HttpStatus.OK,
                this.categoryService.getAllCategory(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOne(@PathVariable("id") String id){
        return ResponseHandler.generateResponse("Category", HttpStatus.OK, this.categoryService.getById(id));
    }


    @PostMapping()
    public ResponseEntity<Object> saveCategory(@Validated @RequestBody CreateCategoryDto createCategoryDto){
        return ResponseHandler.generateResponse(
                "Category saved", HttpStatus.OK,
                this.categoryService.addCategory(createCategoryDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("id") String id){
        return ResponseHandler.generateResponse("Category deleted", HttpStatus.OK, this.categoryService.deleteCategory(id));

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable("id") String id, @RequestBody UpdateCategoryDto updateCategoryDto){
        return ResponseHandler.generateResponse("Category updated", HttpStatus.OK, this.categoryService.updateCategory(updateCategoryDto, id));

    }

    @GetMapping("/test")
    public boolean testCategories(@RequestBody CategoriesDto categories) {
        return this.categoryService.allCategoriesExistInDB(categories.getCategories());
    }
}
