package com.backend.cars.validation;

import com.backend.cars.model.User;
import com.backend.cars.service.UserService;
import com.backend.cars.validators.UniqueEmailValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
public class UserValidationTest {

    private UniqueEmailValidator uniqueEmailValidator;

    @Mock
    UserService userService;

    @Before
    public void setUp(){
        this.uniqueEmailValidator = new UniqueEmailValidator(userService);
        Mockito.when(userService.getUserByEmail("andrzej@gmail.com")).thenReturn(new User());
        Mockito.when(userService.getUserByEmail("mati@gmail.com")).thenReturn(null);
    }

    @Test
    public void whenEmailIsNotUnique(){
        boolean violation = uniqueEmailValidator.isValid("andrzej@gmail.com", null);
        Assert.assertEquals(false, violation);
    }

    @Test
    public void whenEmailIsUnique(){
        boolean violation = uniqueEmailValidator.isValid("mati@gmail.com", null);
        Assert.assertEquals(true, violation);
    }


}
