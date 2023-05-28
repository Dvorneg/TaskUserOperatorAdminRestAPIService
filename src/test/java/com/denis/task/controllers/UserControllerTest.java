package com.denis.task.controllers;

import com.denis.task.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class UserControllerTest extends AbstractControllerTest{

    @Autowired
    private UserService userService;

    @Test
    void getApplicationsOperator() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/users")
                .with(httpBasic("Operator", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
               // .andExpect(content().json("{\"message\":\"У меня монитор не сенсорный, можно заменить?\"}"));
    }

    @Test
    void getApplicationsAdministrator() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/users")
                .with(httpBasic("Admin", "admin"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", Matchers.hasSize(4)))
                .andExpect(jsonPath("$[0].name", Matchers.containsString("User")))
                .andExpect(jsonPath("$[1].email", Matchers.containsString("operator@yandex.ru")));
    }

}
