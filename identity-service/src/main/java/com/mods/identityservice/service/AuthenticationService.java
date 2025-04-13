package com.mods.identityservice.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mods.identityservice.dto.request.AuthenticationRequest;
import com.mods.identityservice.dto.request.IntrospectRequest;
import com.mods.identityservice.dto.request.LoginRequest;
import com.mods.identityservice.dto.response.AuthenticationResponse;
import com.mods.identityservice.dto.response.IntrospectResponse;
import com.mods.identityservice.dto.response.LoginResponse;
import com.mods.identityservice.dto.response.UserResponse;
import com.mods.identityservice.entity.User;
import com.mods.identityservice.exceptions.AppException;
import com.mods.identityservice.repository.UserRepository;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.JWTClaimsSetVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    ObjectMapper objectMapper;
    private final String SIGN_KEY ="12345678901234567890123456789012";

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier jwtClaimsSetVerifier = new MACVerifier(SIGN_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean isValid = signedJWT.verify(jwtClaimsSetVerifier);
        Date expired = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean isNotExpired = expired.before(new Date());

        return IntrospectResponse.builder().valid(isValid && isNotExpired).build();
    }

    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()
                -> new IllegalArgumentException("User not found"));

        if (!new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return entityToResponse(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws JOSEException, ParseException {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()
                -> new IllegalArgumentException("User not found"));

        if (!new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    private String generateToken(User user) {
        // claim: thong tin, co the tao custom hoac dung mac dinh cua JwtClaimsSet
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("hieunm2")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .subject(user.getId())
                .claim("username", user.getUsername())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .build();
        // header chua thuat toan ma hoa
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);

        try {
            // ky
            signedJWT.sign(new MACSigner(SIGN_KEY.getBytes()));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error("Error while signing JWT", e);
            throw new IllegalArgumentException("Error while signing JWT");
        }
    }

    private UserResponse entityToResponse(User entity) {
        return objectMapper.convertValue(entity, UserResponse.class);
    }


    private record TokenInfo(String token, Date expiryDate) {}
}
