package com.mods.profileservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.service.annotation.GetExchange;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    String id;
    String userId;
    String firstName;
    String lastName;
    LocalDate dob;
    String city;
}
