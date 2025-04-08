package com.mods.identityservice.controller;

import com.mods.identityservice.dto.request.UserCreationRequest;
import com.mods.identityservice.dto.response.UserResponse;
import com.mods.identityservice.repository.feign_client.ProfileClient;
import com.mods.identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public UserResponse createUser(@RequestBody UserCreationRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable String id) {
        return userService.getUser(id);
    }
}
