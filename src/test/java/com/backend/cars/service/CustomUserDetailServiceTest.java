package com.backend.cars.service;

import com.backend.cars.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailServiceTest {
    //tested class
    private CustomUserDetailService customUserDetailService;

    User notNullUser;

    @Mock
    UserService userService;

    @Before
    public void setUp(){
        notNullUser = new User();
        notNullUser.setEmail("notnull@email.com");
        this.customUserDetailService = new CustomUserDetailService(userService);
        Mockito.when(userService.getUserByEmail("null@email.com")).thenReturn(null);
        Mockito.when(userService.getUserByEmail("notnull@email.com")).thenReturn(notNullUser);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenUserDoesntExist(){
        customUserDetailService.loadUserByUsername("null@email.com");
    }

    @Test
    public void whenUserDoesExist(){
        Assert.assertEquals("notnull@email.com", customUserDetailService.loadUserByUsername("notnull@email.com").getUsername());
    }
}
