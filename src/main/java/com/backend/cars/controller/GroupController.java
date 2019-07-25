package com.backend.cars.controller;

import com.backend.cars.model.User;
import com.backend.cars.service.GroupService;
import com.backend.cars.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @GetMapping("/createGroup")
    @ResponseBody
    public String createGroup(@RequestParam(name = "groupName") String groupName){
        groupService.createGroup(groupName);

        return "UserGroup "+groupName+" created!";
    }

    @GetMapping(value = "/getUsers")
    @ResponseBody
    public List<UserTemplate> getUsers(@RequestParam(name = "groupId") int groupId){
        List<UserTemplate> users = groupService.getUsers(groupId).stream().
                map(user -> new UserTemplate(user.getEmail(), user.getUser_id())).collect(Collectors.toList());
        return users;
    }

    @GetMapping("/addUser")
    @ResponseBody
    public String addUser(@RequestParam(name = "userId") int userId, @RequestParam(name = "groupId") int groupId){
        User user = userService.getUserById(userId);

        try {
            groupService.addUser(groupId, user);
            return "User added successfully";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    private class UserTemplate{
        private String email;
        private int id;
    }
}
