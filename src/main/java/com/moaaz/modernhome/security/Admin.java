package com.moaaz.modernhome.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;


@ConfigurationProperties(prefix = "users.admin")
@Configuration
@Data
public class Admin {

    public  String email;
    public  String password;


}
