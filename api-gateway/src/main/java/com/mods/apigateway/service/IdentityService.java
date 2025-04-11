package com.mods.apigateway.service;

import com.mods.apigateway.dto.request.IntrospectRequest;
import com.mods.apigateway.dto.response.ApiResponse;
import com.mods.apigateway.dto.response.IntrospectResponse;
import com.mods.apigateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {

    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {

        return identityClient.introspect(IntrospectRequest.builder().token(token).build());
    }
}
