package com.backend.cars.controller;

import com.backend.cars.model.UserGroup;
import com.backend.cars.model.User;
import com.backend.cars.service.GroupService;
import com.backend.cars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @GetMapping("/createGroup")
    @ResponseBody
    public String createGroup(@RequestParam(name = "adminId") int adminId,@RequestParam(name = "groupName") String groupName){
        userService.createGroup(adminId,groupName);
        return "UserGroup "+groupName+" created!";
    }

    @GetMapping("/getUsers")
    @ResponseBody
    public Set<User> getUsers(@RequestParam(name = "groupId") int groupId){
        return groupService.getUsers(groupId);
    }

    @GetMapping("/addUser")
    @ResponseBody
    public String addUser(@RequestParam(name = "adminId") int adminId,@RequestParam(name = "userId") int userId, @RequestParam(name = "groupId") int groupId){
        User user = userService.getUserById(userId);
        User admin = userService.getUserById(adminId);

        //checking if admin is actually admin od userGroup with id gropuId
        Set<UserGroup> userGroups = admin.getAsAdminUserGroups();
        UserGroup userGroup = groupService.getGroupById(groupId);
        if(userGroups.contains(userGroup)) {
            groupService.addUser(groupId, user);
            return "User added successfully";
        }else {
            return "User is not admin of that userGroup";
        }
    }
}
