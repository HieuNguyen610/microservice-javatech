package com.mods.identityservice.dto.request;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest implements Serializable {
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String password;
}
