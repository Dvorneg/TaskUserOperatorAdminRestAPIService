package com.denis.task.controllers;

import com.denis.task.model.User;
import com.denis.task.security.UserDetailsImpl;
import com.denis.task.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
//@CacheConfig(cacheNames = "users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAll(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        int userId = userDetails.getUser().id();
        return userService.getAll(userId);
    }

    @GetMapping("/{id}")
    public void addOperatorRole(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable int id) {
        int userId = userDetails.getUser().id();
        //Hibernate.initialize(result.getIngredients());
        userService.addOperatorRole(userId); //todo addOperatorRole
        return;
    }


}
