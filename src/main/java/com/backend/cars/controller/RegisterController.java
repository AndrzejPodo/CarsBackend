package com.backend.cars.controller;


import com.backend.cars.model.User;
import com.backend.cars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "/register")
    @ResponseBody
    public String register(@Valid @RequestBody User user){
        HashMap<String, Object> response = new LinkedHashMap<String, Object>();

        String pwd = user.getPassword();
        String encryptPwd = bCryptPasswordEncoder.encode(pwd);
        user.setPassword(encryptPwd);
        userService.addUser(user);
        return "User registered successfully!";
    }
}
