package com.backend.cars.service;

import com.backend.cars.model.UserGroup;
import com.backend.cars.model.User;

import java.util.Set;

public interface GroupService {
    Set<User> getUsers(int groupId);
    void addUser(int groupId, User user);
    UserGroup getGroupById(int groupId);
}
