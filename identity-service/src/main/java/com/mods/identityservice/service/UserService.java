package com.mods.identityservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mods.identityservice.dto.request.UserCreationRequest;
import com.mods.identityservice.dto.response.UserResponse;
import com.mods.identityservice.entity.User;
import com.mods.identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    private UserResponse convertToUserResponse(User user) {
        return objectMapper.convertValue(user, UserResponse.class);
    }

    private User convertToUser(UserCreationRequest request) {
        return objectMapper.convertValue(request, User.class);
    }

    public UserResponse createUser(UserCreationRequest request) {
        User user = convertToUser(request);
        User savedUser = userRepository.save(user);
        return convertToUserResponse(savedUser);
    }

    public UserResponse getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return convertToUserResponse(user);
    }

}
