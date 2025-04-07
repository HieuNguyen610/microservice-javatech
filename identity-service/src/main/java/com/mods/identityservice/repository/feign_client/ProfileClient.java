package com.mods.identityservice.repository.feign_client;

import com.mods.identityservice.dto.request.UserCreationRequest;
import com.mods.identityservice.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "profile-service", url = "http://localhost:8081/profile")
public interface ProfileClient {

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse createUser(UserCreationRequest request);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse getUser(String id);

}
