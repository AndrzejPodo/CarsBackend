package com.backend.cars.service;

import com.backend.cars.exceptions.GroupManagementPermissionDeniedException;
import com.backend.cars.exceptions.UserAlreadyInTheGroupException;
import com.backend.cars.model.User;
import com.backend.cars.model.UserGroup;
import com.backend.cars.repository.GroupRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

    GroupService groupService;

    @Mock
    GroupRepository groupRepository;

    @Mock
    UserService userService;


    User admin;

    User admin2;

    User user;

    UserGroup userGroup;

    Authentication authentication;

    @Before
    public void setUp(){


        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        authentication = Mockito.mock(Authentication.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        userGroup = new UserGroup();
        userGroup.setGroupName("Group");

        admin = new User();
        admin2 = new User();
        user = new User();
        user.setUser_id(1);
        //admin is admin of userGroup and admin2 is not admin of userGroup
        Set<UserGroup> groups = new HashSet<>();
        groups.add(userGroup);
        admin.setManagedGroups(groups);

        Mockito.when(groupRepository.getOne(1)).thenReturn(userGroup);

        groupService = new GroupServiceImpl(groupRepository,userService);
    }

    @Test
    public void whenAdminManagesGroupAndUserIsNotInTheGroup(){

        Mockito.when(authentication.getName()).thenReturn("admin@test.com");
        Mockito.when(userService.getUserByEmail("admin@test.com")).thenReturn(admin);
        Mockito.when(userService.getGroupsThatUserBelongsTo(1)).thenReturn(null);

        try{
            groupService.addUser(1, user);
        }catch (Exception e){
            e.getMessage();
        }

        Assert.assertEquals(1,userGroup.getUsers().size());
        Assert.assertTrue(userGroup.getUsers().contains(user));

    }


    @Test(expected = UserAlreadyInTheGroupException.class)
    public void whenAdminManagesGroupButUserIsAlreadyInGroup() throws Exception{

        Set<UserGroup> groups = new HashSet<>();
        groups.add(userGroup);
        Mockito.when(authentication.getName()).thenReturn("admin@test.com");
        Mockito.when(userService.getUserByEmail("admin@test.com")).thenReturn(admin);
        Mockito.when(userService.getGroupsThatUserBelongsTo(1)).thenReturn(groups);


        groupService.addUser(1, user);

    }

    @Test(expected = GroupManagementPermissionDeniedException.class)
    public void whenAdminCantManageGroup() throws Exception{

        Set<UserGroup> groups = new HashSet<>();
        groups.add(userGroup);
        Mockito.when(authentication.getName()).thenReturn("admin2@test.com");
        Mockito.when(userService.getUserByEmail("admin2@test.com")).thenReturn(admin2);


        groupService.addUser(1, user);

    }
}
