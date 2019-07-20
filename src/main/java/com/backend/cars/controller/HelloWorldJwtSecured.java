package com.backend.cars.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloWorldJwtSecured {
    @RequestMapping("/hello")
    public String helloWorld(){
        return "Hello world!";
    }
}
