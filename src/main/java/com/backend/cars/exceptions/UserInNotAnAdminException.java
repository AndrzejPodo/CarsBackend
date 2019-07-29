package com.backend.cars.exceptions;

public class UserInNotAnAdminException extends Exception {

    public UserInNotAnAdminException(String message){
        super(message);
    }

}
