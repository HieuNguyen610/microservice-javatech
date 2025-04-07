package com.mods.profileservice.controller;

import com.mods.profileservice.dto.request.ProfileCreationRequest;
import com.mods.profileservice.dto.response.UserProfileResponse;
import com.mods.profileservice.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {

    UserProfileService userProfileService;

    @PostMapping("/")
    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest request) {
        return userProfileService.createProfile(request);
    }

    @GetMapping("/{id}")
    UserProfileResponse getProfile(@PathVariable String id) {
        return userProfileService.getProfile(id);
    }
}
