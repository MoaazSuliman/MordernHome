package com.moaaz.modernhome.security.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "No Valid Email")
    private String email;
    @NotNull(message = "password must not be null")
    @NotEmpty(message = "password must not be empty")
    private String password;
}
