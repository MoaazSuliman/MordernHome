package com.moaaz.modernhome.security;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "users.developer")
@Data
public class Developer {

    public String email;
    public String password;
}
