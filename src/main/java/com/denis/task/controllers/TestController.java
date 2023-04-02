package com.denis.task.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping()
    public String getTest(){
        return "Test srtring";  //Jackson -> JSON
    }


}
