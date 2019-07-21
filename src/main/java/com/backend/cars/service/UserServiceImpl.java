package com.backend.cars.service;

import com.backend.cars.model.UserGroup;
import com.backend.cars.model.User;
import com.backend.cars.repository.GroupRepository;
import com.backend.cars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;


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

    @Override
    public Set<UserGroup> getGroupsThatUserBelongsTo(int id){
        User user = getUserById(id);
        return user.getAsUserUserGroups();
    }

    @Override
    public Set<UserGroup> getGroupsThatUserManages(int id){
        User user = getUserById(id);
        return user.getAsAdminUserGroups();
    }

    @Override
    public UserGroup createGroup(int userId, String groupName){
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName(groupName);
        User user = userRepository.getOne(userId);
        Set<UserGroup> set = user.getAsAdminUserGroups();
        set.add(userGroup);
        user.setAsAdminUserGroups(set);
        groupRepository.save(userGroup);
        userRepository.saveAndFlush(user);
        //return groupRepository.saveAndFlush(userGroup);
        return userGroup;
    }
}
