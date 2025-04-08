package com.mods.identityservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mods.identityservice.dto.request.ProfileCreationRequest;
import com.mods.identityservice.dto.request.UserCreationRequest;
import com.mods.identityservice.dto.response.UserResponse;
import com.mods.identityservice.entity.User;
import com.mods.identityservice.repository.UserRepository;
import com.mods.identityservice.repository.feign_client.ProfileClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final ProfileClient profileClient;

    private UserResponse convertToUserResponse(User user) {
        return objectMapper.convertValue(user, UserResponse.class);
    }

    private User convertToUser(UserCreationRequest request) {
        return objectMapper.convertValue(request, User.class);
    }

    private ProfileCreationRequest convertToProfileCreationRequest(UserCreationRequest request) {
        return objectMapper.convertValue(request, ProfileCreationRequest.class);
    }

    public UserResponse createUser(UserCreationRequest request) {

        log.info("Create user {}", request);
        User user = convertToUser(request);
        User savedUser = userRepository.save(user);

        log.info("Create profile for user {}", savedUser.getId());
        ProfileCreationRequest profile = convertToProfileCreationRequest(request);
        profile.setUserId(savedUser.getId());
        profileClient.createUser(profile);

        return convertToUserResponse(savedUser);
    }

    public UserResponse getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return convertToUserResponse(user);
    }

}
