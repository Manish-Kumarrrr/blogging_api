package com.manish.blog.services.impl;

import com.manish.blog.entities.Category;
import com.manish.blog.exceptions.ResourceNotFoundException;
import com.manish.blog.payloads.CategoryDto;
import com.manish.blog.repositories.CategoryRepo;
import com.manish.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=dtoToCategory(categoryDto);
        Category createdCategory=this.categoryRepo.save(category);
        return categoryToDto(createdCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","Id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory=this.categoryRepo.save(category);
        return categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
//        CategoryDto categoryDto=this.getCategoryById(categoryId);
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","Id",categoryId));
        this.categoryRepo.delete(category);

    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","Id",categoryId));
        return this.categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories=this.categoryRepo.findAll();
        List<CategoryDto> categoryDtoList=categories.stream().map((category)-> categoryToDto(category)).collect(Collectors.toList());
        return categoryDtoList;
    }

    private CategoryDto categoryToDto(Category category){
        CategoryDto categoryDto=this.modelMapper.map(category,CategoryDto.class);
        return categoryDto;
    }

    private Category dtoToCategory(CategoryDto categoryDto){
        Category category=this.modelMapper.map(categoryDto,Category.class);
        return category;
    }

}
