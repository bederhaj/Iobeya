package com.iobeya.category.repository;

import com.iobeya.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {

    boolean existsByParentID(int categoryID);

    List<CategoryEntity> findByCategoryNameContains(String name);

    Integer countByCatLevel(int categoryLevel);

    Integer countByParentID(Integer categoryID);

    List<CategoryEntity> findByParentID(Integer categoryID);

    CategoryEntity findByCategoryName(String name);
}
