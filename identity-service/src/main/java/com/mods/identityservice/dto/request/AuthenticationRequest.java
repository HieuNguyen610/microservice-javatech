package com.mods.identityservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    private String username;
    private String password;
}
