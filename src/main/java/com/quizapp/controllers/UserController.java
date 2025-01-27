package com.quizapp.controllers;

import com.quizapp.ingestion.User;
import com.quizapp.services.UserService;
import com.quizapp.utils.ApiResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable("userId") String userId){
        val user = this.userService.getUserById(userId);
        return new ResponseEntity<>(ApiResponse.builder().status(true).message("User found Successfully with Id: "+ userId).data(user).build() ,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user){
        val userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        val createdUser = this.userService.createUser(user);
        return new ResponseEntity<>(ApiResponse.builder().status(true).message("User created Successfully with Id: "+ createdUser.getUserId()).data(createdUser).build() ,HttpStatus.CREATED);
    }

}
