<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
      </ul>
    </li>
    <li><a href="#usage">End points</a></li>
   
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

this project 'category' is for the iobeya test 'EDF' :smile:

 ```
DISCLAIMER : 

here are the tasks i didn't got the time to finish or partially finish : 

- the script to aliment the data base  for now a method runs at app lunch to insert some data.
- the unit tests
 ```


### Built With

this project is built with

* Java 17
* spring boot 2.7.8
* postgreSQL

Used for auth : spring security
     for DB : spring data : jpa/hibernate
 
project build on maven




<!-- GETTING STARTED -->
## Getting Started

Start the project

### Prerequisites

the data base runs on pgAdmin4 so we need to create it with credentials : 

user : postgres /
pass : admin 

here is the config used to connect to data base 
 ```
 #--------------------- DB Connection ------------------
    spring.datasource.url=jdbc:postgresql://localhost:5432/category_users
spring.datasource.username=postgres
spring.datasource.password=admin

#--------------------JPA-ORM Properties-----------------
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto= create-drop
spring.jpa.hibernate.hbm2ddl.auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL9Dialect
spring.jpa.properties.hibernate.format_sql=true

  ```



<!-- USAGE EXAMPLES -->
## End points

Is used POSTMAN  to test all the endpoints with basic auth as shown under : 

![image](https://user-images.githubusercontent.com/30043326/216849321-1a689dd0-6ccd-42b7-b547-c3bad1a72f6e.png)

Same credential can be used :smile:

url : 
  ```
  http://localhost:8081/category/**
  ```

end points : 

  ```
 @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories();   ==> to get all categories
 
 @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer categoryID); ==> to delete a category
    
  @PutMapping("/{id}")
    public  ResponseEntity<String> updateCategoryName(@PathVariable("id") Integer categoryID,@RequestParam String name) ==> change a category name
    
  @PostMapping("/new-category")
    public  ResponseEntity<CategoryEntity> addCategory(@RequestBody CategoryDto categoryDto) ==> add a category root or child
    
   @GetMapping("/name-category")
    public  ResponseEntity<List<CategoryDto>> getCategoryByName(@RequestParam String name) ==> get category by name or partially
    
 @GetMapping("navigate/{id}")
    public  ResponseEntity<CategoryDto> navigateToCategory(@PathVariable("id") Integer categoryID) ==> navigate in categories
  ```
