package com.denis.task.controllers;

import com.denis.task.dto.ApplicationDTO;
import com.denis.task.model.Application;
import com.denis.task.model.Status;
import com.denis.task.service.ApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ApplicationControllerTest extends AbstractControllerTest{

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationControllerTest(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Test//+
    void getApplicationsUser() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/app/2")
                .with(httpBasic("User", "password")))
                //.contentType(MediaType.APPLICATION_JSON)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"На мониторе написано LG, что делать?\"}"));
    }

    @Test //+
    void getApplicationsOtherUser() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/app/3")
                .with(httpBasic("User", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"Нет доступа к выбранной заявке!\"}"));
    }

    @Test //+
    void getApplicationsOperator() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/app/4")
                .with(httpBasic("Operator", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"У меня монитор не сенсорный, можно заменить?\"}"));
    }

    @Test//+
    void getNothingApplicationsWithUserRole() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/app/200")
                .with(httpBasic("User", "password")))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"Не найдена заявка по номеру:200\"}"));
    }

    @Test
    void getWithPagination() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/app?page=1&sortASC=false")
                .with(httpBasic("Operator", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"status\":\"DRAFT\",\"message\":\"Перезвоните срочно! Как можно не работать в выходные!\",\"applicationDateTime\":\"2020-01-30T10:00:00\"}]"));
    }


    @Test
    void NewApplication() throws Exception {

        //ApplicationDTO applicationDTO = new ApplicationDTO("новая заявка черновик");
        perform(MockMvcRequestBuilders.post("/api/app/add")
                .with(httpBasic("User", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                //.content(String.valueOf(applicationDTO)))
                .content("{\"message\":\"новая заявка черновик\"}"))
                .andExpect(status().isCreated());

        //Assertions.assertMatch(mealRepository.getById(MEAL1_ID), updated);
    }

    @Test
    public void UserNotAutorized() throws Exception {
        ApplicationDTO applicationDTO = new ApplicationDTO("новая заявка черновик");
        //application.setMessage();

        perform(MockMvcRequestBuilders.post("/api/app/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(applicationDTO)))
                .andExpect(status().isUnauthorized());

    }



    @Test
    void add() throws Exception {

        ApplicationDTO applicationDTO = new ApplicationDTO("новая заявка черновик");
        String json = new ObjectMapper(). writeValueAsString (applicationDTO);

        perform(MockMvcRequestBuilders.post("/api/app/add")
                .with(httpBasic("User", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                //.content(String.valueOf(applicationDTO)))
                .content(json))
                .andExpect(status().isCreated());
    }

    @Disabled("А надо ли обновлять по тз?")
    @Test
    void update() {

    }

 /*   @Test
    void get() {getApplications  }*/


    //Send app to operator Status.SEND
    @Test
    void send() throws Exception {

        Integer applicationId = 2;
        //ApplicationDTO applicationDTO = new ApplicationDTO("новая заявка черновик");
        //String json = new ObjectMapper(). writeValueAsString (applicationDTO);
        Application applicationBefore=applicationService.getApplication(applicationId, 2);
        Assertions.assertEquals(applicationBefore.getStatus(),Status.DRAFT);

        perform(MockMvcRequestBuilders.get("/api/app/send/"+applicationId)
                .with(httpBasic("User", "password"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Application application=applicationService.getApplication(applicationId, 2);
        Assertions.assertEquals(application.getStatus(),Status.SEND);

    }
}