package com.denis.task.controllers;

import com.denis.task.dto.ApplicationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApplicationControllerTest extends AbstractControllerTest{

    //•	Создать заявку (Заявка помимо прочих системных полей состоит из статуса и текстового обращения пользователя)
    @Test
    void NewApplication() throws Exception {

        ApplicationDTO applicationDTO = new ApplicationDTO("новая заявка черновик");
        perform(MockMvcRequestBuilders.post("/api/app/add")
                .with(httpBasic("User", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                //.content(String.valueOf(applicationDTO)))
                .content("{\"message\":\"новая заявка черновик\"}"))
                .andExpect(status().isOk());

        //Assertions.assertMatch(mealRepository.getById(MEAL1_ID), updated);
    }

/*    @Test
    void handleCreateNewTask_PayloadIsValid_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.post("/api/app/add")
                .with(httpBasic("user2", "password2"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"новая заявка черновик\"}");

        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isCreated(),
                        header().exists(HttpHeaders.LOCATION),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "details": "Третья задача",
                                    "completed": false
                                }
                                """),
                        jsonPath("$.id").exists()
                );
    }*/


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
    void getApplications() {
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void get() {
    }

    @Test
    void send() {
    }
}