package com.iobeya.category.helper;

import com.iobeya.category.dto.CategoryDto;
import com.iobeya.category.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryMapper {

    public static CategoryDto mapCategory(CategoryEntity categoryEntity){
        return new CategoryDto().builder()
                .categoryId(categoryEntity.getCategoryId())
                .categoryName(categoryEntity.getCategoryName())
                .parentId(categoryEntity.getParentID())
                .catLevel(categoryEntity.getCatLevel())
                .categoryChilds(new ArrayList<>())
                .build();
    }


    public static CategoryEntity mapCategoryDto(CategoryDto categoryDto){
        return new CategoryEntity().builder()
                .categoryId(categoryDto.getCategoryId())
                .categoryName(categoryDto.getCategoryName())
                .catLevel(categoryDto.getCatLevel())
                .parentID(Optional.of(categoryDto.getParentId()).orElse(0))
                .build();
    }

    public static List<CategoryDto> mapCategories(List<CategoryEntity> categoryEntityList){
        List<CategoryDto> categoryDtoList = new ArrayList<CategoryDto>();
        categoryEntityList.stream().forEach(categoryEntity -> categoryDtoList.add(mapCategory(categoryEntity)));
        return categoryDtoList;

    }

}
