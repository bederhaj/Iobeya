package com.iobeya.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Integer categoryId;
    private String categoryName;
    private Integer parentId;
    private Integer catLevel;
    private List<CategoryDto> categoryChilds;

    public void setChildToList(CategoryDto categoryDto){this.categoryChilds.add(categoryDto);}

}
