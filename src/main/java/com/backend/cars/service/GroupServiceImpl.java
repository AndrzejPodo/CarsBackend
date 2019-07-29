package com.backend.cars.service;

import com.backend.cars.exceptions.GroupManagementPermissionDeniedException;
import com.backend.cars.exceptions.UserAlreadyInTheGroupException;
import com.backend.cars.model.UserGroup;
import com.backend.cars.model.User;
import com.backend.cars.repository.GroupRepository;
import com.backend.cars.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserService userService;

    @Override
    public List<User> getUsers(int groupId) throws Exception {
        User admin = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        UserGroup userGroup = groupRepository.getOne(groupId);

        //if admin is some way tries to add user to group to which he dont have rights
        if(admin.getManagedGroups() == null || !admin.getManagedGroups().contains(userGroup))
            throw new GroupManagementPermissionDeniedException("User do not have permission to mange this group!");


        return new ArrayList(userGroup.getUsers());
    }

    @Override
    public void addUser(int groupId, User user) throws Exception{
        User admin = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        UserGroup userGroup = groupRepository.getOne(groupId);

        //if admin is some way tries to add user to group to which he dont have rights
        if(admin.getManagedGroups() == null || !admin.getManagedGroups().contains(userGroup))
            throw new GroupManagementPermissionDeniedException("User do not have permission to mange this group!");

        if(userService.getGroupsThatUserBelongsTo(user.getUser_id())!= null && userService.getGroupsThatUserBelongsTo(user.getUser_id()).contains(userGroup))
            throw new UserAlreadyInTheGroupException("User is already in that group!");

        Set<User> set = userGroup.getUsers();
        if(set == null) set = new HashSet<>();
        set.add(user);
        userGroup.setUsers(set);

        groupRepository.save(userGroup);
    }
    @Override
    public UserGroup getGroupById(int groupId){
        return groupRepository.getOne(groupId);
    }

    @Override
    public void createGroup(String groupName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User creator = userService.getUserByEmail(authentication.getName());

        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName(groupName);

        Set<UserGroup> set = creator.getManagedGroups();
        set.add(userGroup);
        creator.setManagedGroups(set);
        groupRepository.save(userGroup);
        userService.addUser(creator);
    }
}
