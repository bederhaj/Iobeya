package com.iobeya.category.service.impl;


import com.iobeya.category.dto.CategoryDto;
import com.iobeya.category.entity.CategoryEntity;
import com.iobeya.category.exception.CategoryException;
import com.iobeya.category.helper.CategoryMapper;
import com.iobeya.category.repository.CategoryRepository;
import com.iobeya.category.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {


    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<CategoryDto> getCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "parentID"));
        return mapCategories(categoryEntityList);
    }

    @Override
    public String deleteCategory(int categoryID) {
        if(!categoryRepository.existsByParentID(categoryID)){
            categoryRepository.deleteById(categoryID);
            return "Deletion success";
        }

         throw new CategoryException("Deletion failed");
    }

    @Override
    public String modifyCategoryName(int categoryID, String name) {
        Optional<CategoryEntity> categoryEntity= categoryRepository.findById(categoryID);
        if(categoryEntity.isPresent()){
            categoryEntity.get().setCategoryName(name);
            categoryRepository.save(categoryEntity.get());
            return name;
        }
        return "not updated because not found";
    }

    @Override
    public CategoryEntity addCategory(CategoryDto categoryDto, Optional<Integer> range) {
        CategoryEntity categoryEntity = CategoryMapper.mapCategoryDto(categoryDto);

        if( (0==categoryDto.getCatLevel() && categoryRepository.countByCatLevel(0)<1000)
           || (0!=categoryDto.getCatLevel() && categoryRepository.countByParentID(categoryDto.getParentId())<10)) {
            categoryRepository.save(categoryEntity);
        }


        categoryDto.setCategoryId(categoryRepository.findByCategoryName(categoryDto.getCategoryName()).getCategoryId());
        List<CategoryEntity> categoryEntityChilds= generateRandomChilds(categoryDto,range);
        if(categoryDto.getCatLevel()<9) categoryRepository.saveAll(categoryEntityChilds);


        return categoryEntity;
    }

    private List<CategoryEntity> generateRandomChilds(CategoryDto categoryDto,Optional<Integer> num){
        List<CategoryEntity> categoryEntityList= new ArrayList<>();
        if(categoryDto.getCatLevel()<9) {
            int range = num.orElse(new Random().nextInt(9)+1);
            for (int i = 0; i < range; i++) {
             CategoryEntity categoryEntity=   CategoryEntity.builder()
                        .parentID(categoryDto.getCategoryId())
                        .categoryName(generateCategoryName(categoryDto))
                        .catLevel(categoryDto.getCatLevel()+1)
                        .build();

             categoryEntityList.add(categoryEntity);

            }

        }

        return categoryEntityList ;

    }

    public String generateCategoryName(CategoryDto categoryDto){
        Set<CategoryEntity> categoryEntityList= new HashSet<>(categoryRepository.findAll());
            List<String> set = new Random().ints(0, 10)
                .distinct()
                .limit(10)
                .boxed()
                    .map(e->e+"")
                .collect(Collectors.toList());
        String categoryName = "category".concat(String.join(".",set));
        String finalCategoryName="";
        while(categoryName.equals(categoryDto.getCategoryName())
                ||categoryEntityList.stream().anyMatch(e->categoryName.equals(e.getCategoryName()))){
            List<String> suffix = new Random().ints(0, 10)
                    .distinct()
                    .limit(10)
                    .boxed()
                    .map(e->e+"")
                    .collect(Collectors.toList());
            finalCategoryName = "category".concat(String.join(".",suffix));
        }

        return finalCategoryName.length()==0?categoryName:finalCategoryName;
    }

    @Override
    public List<CategoryDto> getCategoryByName(String name) {
        List<CategoryDto> categoriesByName = CategoryMapper.mapCategories(categoryRepository.findByCategoryNameContains(name));
        return categoriesByName;
    }

    @Override
    public CategoryDto navigateToCategory(Integer categoryID) {
        CategoryDto categoryDto= CategoryMapper.mapCategory(categoryRepository.findById(categoryID).get());
        List<CategoryDto> listOfChilds = CategoryMapper.mapCategories(categoryRepository.findByParentID(categoryID));
        categoryDto.setCategoryChilds(listOfChilds);
        return categoryDto;
    }


    private List<CategoryDto> mapCategories(List<CategoryEntity> categoryEntityList){
        List<CategoryDto> categoryDtoListResult= new ArrayList<>();
        //map list of entity
        List<CategoryDto> categoryDtoList=CategoryMapper.mapCategories(categoryEntityList);
        Map<Integer,CategoryDto> categoryDtoMap=new HashMap<>();
        categoryDtoList.stream().forEach(category-> categoryDtoMap.put(category.getCategoryId(),category ));
        categoryDtoList.stream().forEach(categoryDto -> {
            if(categoryDto.getParentId()>0){
                    CategoryDto parenCategory = categoryDtoMap.get(categoryDto.getParentId());
                    parenCategory.getCategoryChilds().add(categoryDto);
            }else{
                categoryDtoListResult.add(categoryDto);
            }
        });
        return categoryDtoListResult;
    }

}
