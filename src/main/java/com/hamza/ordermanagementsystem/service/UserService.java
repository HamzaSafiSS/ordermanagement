package com.hamza.ordermanagementsystem.service;

import com.hamza.ordermanagementsystem.dto.request.CreateUserRequest;
import com.hamza.ordermanagementsystem.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUser(Long id);
}