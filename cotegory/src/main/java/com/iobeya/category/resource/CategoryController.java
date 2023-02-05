package com.iobeya.category.resource;


import com.iobeya.category.dto.CategoryDto;
import com.iobeya.category.entity.CategoryEntity;
import com.iobeya.category.exception.CategoryException;
import com.iobeya.category.repository.CategoryRepository;
import com.iobeya.category.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class CategoryController {



    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer categoryID){
            try{
                return ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteCategory(categoryID));
            }catch (CategoryException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deletion failed");
            }
    }

    @PutMapping("/{id}")
    @ExceptionHandler(CategoryException.class)

    public  ResponseEntity<String> updateCategoryName(@PathVariable("id") Integer categoryID,@RequestParam String name){

        try{
            return new ResponseEntity<String>(categoryService.modifyCategoryName(categoryID,name),HttpStatus.OK);
        }catch (CategoryException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("update failed");
        }
    }

    @PostMapping("/new-category")
    public  ResponseEntity<CategoryEntity> addCategory(@RequestBody CategoryDto categoryDto){

        try{
            return new ResponseEntity<CategoryEntity>(categoryService.addCategory(categoryDto,null),HttpStatus.OK);
        }catch (CategoryException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CategoryEntity());
        }

    }

    @GetMapping("/name-category")
    public  ResponseEntity<List<CategoryDto>> getCategoryByName(@RequestParam String name){
        return new ResponseEntity<List<CategoryDto>>(categoryService.getCategoryByName(name),HttpStatus.OK);

    }


    @GetMapping("navigate/{id}")
    public  ResponseEntity<CategoryDto> navigateToCategory(@PathVariable("id") Integer categoryID){
        return new ResponseEntity<CategoryDto>(categoryService.navigateToCategory(categoryID),HttpStatus.OK);

    }
}
