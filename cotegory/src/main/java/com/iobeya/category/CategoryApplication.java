package com.iobeya.category;

import com.iobeya.category.dto.CategoryDto;
import com.iobeya.category.entity.UserEntity;

import com.iobeya.category.helper.CategoryMapper;
import com.iobeya.category.repository.CategoryRepository;
import com.iobeya.category.repository.UserRepository;
import com.iobeya.category.service.impl.CategoryServiceImpl;
import com.iobeya.category.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootApplication
public class CategoryApplication {


	private  static final Logger log= LoggerFactory.getLogger(CategoryApplication.class);


	@Bean
	public CommandLineRunner addUsers(UserServiceImpl userService, UserRepository userRepository){
		return (args -> {

			// add ADMIN USER
			if(!userRepository.findByUserName("sabrine").isPresent()) {
				userService.addUser(new UserEntity(1, "sabrine","sabrine", "ROLE_ADMIN"));
			}

			for(UserEntity userEntity : userRepository.findAll()){
				log.info("User added : "+userEntity.toString());
			}
		});
	}

	@Bean
	public CommandLineRunner addCategory(CategoryRepository categoryRepository, CategoryServiceImpl categoryService){
		return (args -> {
			List<CategoryDto> categoryDtoList=new ArrayList<>();
			CategoryDto categoryDtoFirst = new CategoryDto(1,"category1.2.7.1.4.9.8.9.7.8",0,0,null);
			categoryRepository.save(CategoryMapper.mapCategoryDto(categoryDtoFirst));

			for(int i =1 ; i<999; i++){
				CategoryDto categoryDto= new CategoryDto(i,categoryService.generateCategoryName(categoryDtoFirst),0,0,null);
				categoryDtoList.add(categoryDto);
				categoryRepository.save(CategoryMapper.mapCategoryDto(categoryDto));

			}

			categoryDtoList.stream().forEach(categoryDto -> {
				CategoryDto categoryDtoLvl1= new CategoryDto(1,categoryService.generateCategoryName(categoryDto),categoryDto.getCategoryId(),1,null);

				categoryService.addCategory(categoryDtoLvl1, Optional.of(10));
			});

		});
	}

	public static void main(String[] args) {
		SpringApplication.run(CategoryApplication.class, args);
	}

}
