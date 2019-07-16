package com.backend.cars.controller;


import com.backend.cars.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.cars.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager dataSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public String register(@Valid @RequestBody User user){
        userRepository.save(user);
        return "User name: "+user.getUsername()+" Password: "+user.getPassword();
    }
}
