package com.iobeya.category.service.impl;


import com.iobeya.category.entity.UserEntity;
import com.iobeya.category.repository.UserRepository;
import com.iobeya.category.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String addUser(UserEntity userEntity){
        String encryptedPassword= bCryptPasswordEncoder.encode(userEntity.getPassWord());
        userEntity.setRole("ROLE_ADMIN");
        userEntity.setPassWord(encryptedPassword);
        userRepository.save(userEntity);
        return userEntity.toString();
    }
}