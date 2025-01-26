package com.quizapp.controllers;

import com.quizapp.ingestion.User;
import com.quizapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") String userId){
        return this.userService.getUserById(userId);
    }
}
