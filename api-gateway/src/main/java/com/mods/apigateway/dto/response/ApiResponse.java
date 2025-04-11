package com.mods.apigateway.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse <T> {

    @Builder.Default
    private int code = 1000;
    private String message;
    private T data;
}
