package com.backend.cars.service;

import com.backend.cars.model.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.List;

public interface UserService {
    List<User> getUserByName(String name);
    User getUserByEmail(String email);
    User getUserById(int id);
    User addUser(User user);
}
