package com.iobeya.category.resource;



import com.iobeya.category.entity.UserEntity;
import com.iobeya.category.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/auth")
    public String joinUser(@RequestBody UserEntity user){
        return   userService.addUser(user);

    }
}