package com.iobeya.category.service;

import com.iobeya.category.dto.CategoryDto;
import com.iobeya.category.entity.CategoryEntity;
import com.iobeya.category.exception.CategoryException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    List<CategoryDto> getCategories();
    @Transactional
    String  deleteCategory(int categoryID) throws CategoryException;

    String modifyCategoryName(int categoryID, String name);

    CategoryEntity addCategory(CategoryDto categoryDto, Optional<Integer> range);
    List<CategoryDto> getCategoryByName(String name);

   CategoryDto navigateToCategory(Integer categoryID);
}
