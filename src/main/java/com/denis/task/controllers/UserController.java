package com.denis.task.controllers;

import com.denis.task.model.User;
import com.denis.task.security.UserDetailsImpl;
import com.denis.task.service.UserService;
import com.denis.task.util.ApplicationErrorResponse;
import com.denis.task.util.ApplicationException;
import com.denis.task.util.UserErrorResponse;
import com.denis.task.util.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<HttpStatus> addOperatorRole(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable int id) {
        //int userId = userDetails.getUser().id();
        log.info("add role operator to user id  {}", id);
        userService.addOperatorRole(id, userDetails.getUser().id());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search/{name}")
    public List<User>  addOperatorRole(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable String name) {
        //int userId = userDetails.getUser().id();
        log.info("search user  by name  {}", name);
        return userService.findUserByName(name, userDetails.getUser().id());
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserException e){

        UserErrorResponse response = new UserErrorResponse(
                e.getMessage()
                //log.info(System.currentTimeMillis())
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );  //400, BAD_REQUEST
    }

}
