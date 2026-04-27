package com.hamza.ordermanagementsystem.controller;

import com.hamza.ordermanagementsystem.dto.request.CreateUserRequest;
import com.hamza.ordermanagementsystem.dto.response.UserResponse;
import com.hamza.ordermanagementsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}