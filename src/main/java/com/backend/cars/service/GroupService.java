package com.backend.cars.service;

import com.backend.cars.model.UserGroup;
import com.backend.cars.model.User;

import java.util.List;

public interface GroupService {
    List<User> getUsers(int groupId);
    void addUser(int groupId, User user) throws Exception;
    UserGroup getGroupById(int groupId);
    void createGroup(String groupName);
}
