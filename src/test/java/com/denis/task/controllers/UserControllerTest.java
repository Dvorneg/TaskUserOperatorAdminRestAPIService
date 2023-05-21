package com.denis.task.controllers;

import com.denis.task.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

class UserControllerTest extends AbstractControllerTest{

    @Autowired
    private UserService userService;

    @Test
    void TestController() throws Exception {
        perform(MockMvcRequestBuilders.get("/"))
                .andExpect(content().string(containsString("Test srtring")));
    }

    @Test
    @Disabled("Сделать позже")
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isUnauthorized());

    }





}
