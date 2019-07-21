package com.backend.cars.service;

import com.backend.cars.model.UserGroup;
import com.backend.cars.model.User;
import com.backend.cars.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Override
    public Set<User> getUsers(int groupId) {
        UserGroup userGroup = groupRepository.getOne(groupId);
        return userGroup.getUsersAsUser();
    }

    @Override
    public void addUser(int groupId, User user) {
        UserGroup userGroup = groupRepository.getOne(groupId);
        Set<User> set = userGroup.getUsersAsUser();
        set.add(user);
        userGroup.setUsersAsUser(set);
        groupRepository.save(userGroup);
    }
    @Override
    public UserGroup getGroupById(int groupId){
        return groupRepository.getOne(groupId);
    }
}
