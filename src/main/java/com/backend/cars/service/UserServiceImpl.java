package com.backend.cars.service;

import com.backend.cars.model.User;
import com.backend.cars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> userList = userRepository.findByEmail(email);
        return (userList.size() == 0)?null:userList.get(0);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getOne(id);
    }

    @Override
    public User addUser(User user){
        return userRepository.save(user);
    }
}
