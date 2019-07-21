package com.backend.cars.service;

import com.backend.cars.model.UserGroup;
import com.backend.cars.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getUserByName(String name);
    User getUserByEmail(String email);
    User getUserById(int id);
    User addUser(User user);
    Set<UserGroup> getGroupsThatUserBelongsTo(int id);
    Set<UserGroup> getGroupsThatUserManages(int id);
    UserGroup createGroup(int userId, String groupName);
}
