package com.backend.cars.validators;

import com.backend.cars.model.User;
import com.backend.cars.repository.UserRepository;
import com.backend.cars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Service
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private UserService userService;

    @Autowired
    public UniqueEmailValidator(UserService userService){
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User user = userService.getUserByEmail(value);
        if(user != null) return false;
        else return true;
    }
}
