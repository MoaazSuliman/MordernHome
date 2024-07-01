package com.moaaz.modernhome.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService {

    @Autowired
    private Admin admin;

    @Autowired
    private Developer developer;

    public Optional<Admin> findAdminByEmail(String email) {
        if (admin.email.equals(email))
            return Optional.of(admin);
        return Optional.empty();
    }

    public Optional<Developer> findDeveloperByEmail(String email) {
        if (developer.email.equals(email))
            return Optional.of(developer);
        return Optional.empty();
    }
}
