package com.hamza.ordermanagementsystem.service.impl;

import com.hamza.ordermanagementsystem.dto.request.CreateUserRequest;
import com.hamza.ordermanagementsystem.dto.response.UserResponse;
import com.hamza.ordermanagementsystem.entity.User;
import com.hamza.ordermanagementsystem.exception.ResourceNotFoundException;
import com.hamza.ordermanagementsystem.repository.UserRepository;
import com.hamza.ordermanagementsystem.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    public UserResponse getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}