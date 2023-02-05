package com.iobeya.category.service.impl;

import com.iobeya.category.entity.UserEntity;
import com.iobeya.category.helper.MyUser;
import com.iobeya.category.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserAuthServiceImpl implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUserName(username);

       return user.map(MyUser::new)
               .orElseThrow(()->new UsernameNotFoundException(username+" not found"));
    }
}