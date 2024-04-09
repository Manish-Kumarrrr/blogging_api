package com.manish.blog.services;

import com.manish.blog.entities.Category;
import com.manish.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    // in interface all method and member are bydefault public

    // create
    public CategoryDto createCategory(CategoryDto categoryDto);


    // update
    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

    // delete
    void deleteCategory (Integer categoryId);


    // get
    public CategoryDto getCategoryById(Integer categoryId);

    // getAll
    public List<CategoryDto> getAllCategory();


    //
}
