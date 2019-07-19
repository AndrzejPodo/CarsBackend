package com.backend.cars.validation;

import com.backend.cars.model.Role;
import com.backend.cars.model.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

public class UserValidationTest {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void correctUserTest(){
        User user = new User();

        Set<Role> notEmptySet = new HashSet<>();
        Role userRole = new Role();
        userRole.setRole("USER");
        notEmptySet.add(userRole);


        user.setPassword("abcd1234");//8 character password
        user.setEmail("abcd@dhe.com");//correct email format
        user.setUsername("alala");//not null user name
        user.setRoles(notEmptySet);//not empty and not null set of roles

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assert.assertEquals(0, violations.size());
    }
}
