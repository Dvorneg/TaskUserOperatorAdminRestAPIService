package com.denis.task.controllers;


import com.denis.task.dto.ApplicationDTO;
import com.denis.task.model.Application;
import com.denis.task.model.Status;
import com.denis.task.model.User;
import com.denis.task.service.UserService;
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
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isUnauthorized());

    }





}
