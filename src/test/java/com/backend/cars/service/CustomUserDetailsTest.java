package com.backend.cars.service;

import com.backend.cars.model.Role;
import com.backend.cars.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsTest {
    //tested class
    CustomUserDetails customUserDetails;

    @Mock
    User admin;

    @Mock
    User user;

    @Mock
    User adminAndUser;

    Role adminRole;
    Role userRole;

    Set<Role> userRoleSet;

    Set<Role> adminRoleSet;

    Set<Role> adminAndUserRoleSet;

    List<GrantedAuthority> expected;
    List<GrantedAuthority> actual;

    @Before
    public void setUp(){

        userRoleSet = new HashSet<Role>();
        adminRoleSet = new HashSet<Role>();
        adminAndUserRoleSet = new HashSet<Role>();

        adminRole = new Role();
        userRole = new Role();

        adminRole.setRole_id(123);
        adminRole.setRole("ADMIN");

        userRole.setRole_id(124);
        userRole.setRole("USER");

        userRoleSet.add(userRole);

        adminRoleSet.add(adminRole);

        adminAndUserRoleSet.add(userRole);
        adminAndUserRoleSet.add(adminRole);

        Mockito.when(user.getRoles()).thenReturn(userRoleSet);
        Mockito.when(admin.getRoles()).thenReturn(adminRoleSet);
        Mockito.when(adminAndUser.getRoles()).thenReturn(adminAndUserRoleSet);

    }

    @Test
    public void forUser(){
        expected = new ArrayList<>();
        expected.add(new SimpleGrantedAuthority("ROLE_USER"));
        customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);
        
        assertThat(customUserDetails.getAuthorities(), containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void forAdmin(){
        expected = new ArrayList<>();
        expected.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(admin);
        assertThat(customUserDetails.getAuthorities(), containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void forAdminAndUser(){
        expected = new ArrayList<>();
        expected.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        expected.add(new SimpleGrantedAuthority("ROLE_USER"));
        customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(adminAndUser);
        actual = (List<GrantedAuthority>) customUserDetails.getAuthorities();

        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }
}
