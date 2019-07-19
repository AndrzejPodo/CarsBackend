package com.backend.cars.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(RegisterController.class)
public class RegisterController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegisterController registerController;

    public void uniqueValidationTest() throws Exception {
        mvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(createUserJson("Andrzej", "123xdxd")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", is(true)));
    }


    private static String createUserJson(String username, String password){
        return "{\"username\":\""+username+"\",\"password\":\""+password+"\"}";
    }
}
