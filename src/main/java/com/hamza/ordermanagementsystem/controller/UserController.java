package com.hamza.ordermanagementsystem.controller;

import com.hamza.ordermanagementsystem.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test-lifecycle")
    public String testLifecycle() {
        userService.testLifecycle();
        return "Check console logs!";
    }
}