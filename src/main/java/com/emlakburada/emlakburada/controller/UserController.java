package com.emlakburada.emlakburada.controller;

import com.emlakburada.emlakburada.dto.request.UserRequest;
import com.emlakburada.emlakburada.dto.response.UserResponse;
import com.emlakburada.emlakburada.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(required = false) int userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }
}