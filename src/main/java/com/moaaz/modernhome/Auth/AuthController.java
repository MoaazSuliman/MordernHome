package com.moaaz.modernhome.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/moaaz/api/modernhome/auth")
@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        return authService.userLogin(email, password);
    }

    @PostMapping("/adminDashboardLogin")
    public ResponseEntity<?> adminDashboardLogin(@RequestParam String email, @RequestParam String password) {
        return authService.adminDashboardLogin(email, password);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestParam String email) {
        return authService.forgetPassword(email);
    }



}
