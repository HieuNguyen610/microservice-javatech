package com.mods.identityservice.dto.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String password;
}
