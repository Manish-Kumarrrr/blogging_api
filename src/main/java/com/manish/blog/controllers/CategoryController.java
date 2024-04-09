package com.manish.blog.controllers;


import com.manish.blog.payloads.ApiResponse;
import com.manish.blog.payloads.CategoryDto;
import com.manish.blog.services.CategoryService;
import com.manish.blog.services.impl.CategoryServiceImpl;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1=categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
        CategoryDto updatedCategory=categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }


    // delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("category is deleted succesfully",true),HttpStatus.OK);
    }

    //get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){
        CategoryDto categoryDto=this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    // get all
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory( ){
        List<CategoryDto> categoryDtoList=this.categoryService.getAllCategory();
        return new ResponseEntity<>(categoryDtoList,HttpStatus.OK);
    }

}
