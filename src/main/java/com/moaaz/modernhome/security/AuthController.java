package com.moaaz.modernhome.security;

import com.moaaz.modernhome.Auth.AuthService;
import com.moaaz.modernhome.security.dto.LoginRequest;
import com.moaaz.modernhome.security.dto.LoginResponse;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setId(userDetails.getId());
		loginResponse.setEmail(userDetails.getEmail());
		loginResponse.setPassword(userDetails.getPassword());
		loginResponse.setUserRole(userDetails.getUserRole());
		loginResponse.setAuthorities(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));


		return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
	}

	@PostMapping("/forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestParam String email) {
		return authService.forgetPassword(email);
	}
}
