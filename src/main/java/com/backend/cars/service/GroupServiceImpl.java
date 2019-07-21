package com.backend.cars.service;

import com.backend.cars.model.UserGroup;
import com.backend.cars.model.User;
import com.backend.cars.repository.GroupRepository;
import com.backend.cars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUsers(int groupId) {
        UserGroup userGroup = groupRepository.getOne(groupId);
        return new ArrayList(userGroup.getUsers());
    }

    @Override
    public void addUser(int groupId, User user) throws Exception{
        User admin = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        UserGroup userGroup = groupRepository.getOne(groupId);

        if(!admin.getManagedGroups().contains(userGroup)) throw new Exception("User do not have permission to mange this group!");

        Set<User> set = userGroup.getUsers();
        set.add(user);
        userGroup.setUsers(set);

        groupRepository.save(userGroup);
    }
    @Override
    public UserGroup getGroupById(int groupId){
        return groupRepository.getOne(groupId);
    }

    @Override
    public void createGroup(String groupName) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        User creator = userService.getUserByEmail(authentication.getName());

        //just in case spring filters where decepted
        if(!hasUserRole) throw new Exception("User is not permitted to create a group!");

        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName(groupName);

        Set<UserGroup> set = creator.getManagedGroups();
        set.add(userGroup);
        creator.setManagedGroups(set);
        groupRepository.save(userGroup);
        userRepository.saveAndFlush(creator);
    }
}
