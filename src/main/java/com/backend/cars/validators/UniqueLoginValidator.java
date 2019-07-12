package com.backend.cars.validators;

import com.backend.cars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

    @Autowired
    private UserRepository userRepository;

    public UniqueLoginValidator(){
    }

    @Override
    public void initialize(UniqueLogin constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println(userRepository.toString());
        return true;
    }
}
