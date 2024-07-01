package com.moaaz.modernhome.security.dto;


import com.moaaz.modernhome.security.enums.UserRole;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {
    private Long id;
    private String email;
    private String password;
    private List<String> authorities;
    private UserRole userRole ;
}
