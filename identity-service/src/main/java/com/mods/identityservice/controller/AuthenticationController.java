package com.mods.identityservice.controller;

import java.text.ParseException;

import com.mods.identityservice.dto.request.AuthenticationRequest;
import com.mods.identityservice.dto.request.IntrospectRequest;
import com.mods.identityservice.dto.request.LoginRequest;
import com.mods.identityservice.dto.response.*;
import com.mods.identityservice.entity.User;
import com.mods.identityservice.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().data(result).build();
    }

    @PostMapping("/login")
    ApiResponse<UserResponse> login(@RequestBody LoginRequest request) {
        return ApiResponse.<UserResponse>builder().data(authenticationService.login(request)).build();
    }

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> token(@RequestBody AuthenticationRequest request) throws ParseException, JOSEException { //
        return ApiResponse.<AuthenticationResponse>builder().data(authenticationService.authenticate(request)).build();
    }


}