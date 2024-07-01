package com.moaaz.modernhome.Auth;


import com.moaaz.modernhome.Employee.Employee;
import com.moaaz.modernhome.Employee.EmployeeRepository;
import com.moaaz.modernhome.Mail.AuthMailService;
import com.moaaz.modernhome.User.Person;
import com.moaaz.modernhome.User.User;
import com.moaaz.modernhome.User.UserRepository;
import com.moaaz.modernhome.User.UserResponse;
import com.moaaz.modernhome.security.CustomUserDetails;
import com.moaaz.modernhome.security.enums.UserRole;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;

@Service
public class AuthService {


	@Autowired
	private UserRepository userService;

	@Autowired
	private EmployeeRepository employeeService;

	@Autowired
	private AuthMailService authMailService;


	public ResponseEntity<?> userLogin(String email, String password) {

//        if (email.equals("modernhomeinegypt@gmail.com") && password.equals("ModernHome"))
//            return new ResponseEntity<>(Person.admin(), HttpStatus.ACCEPTED);

		User user = userService.findByEmailAndPassword(email, password).orElse(null);
		if (user != null) {
			if (user.isActive()) {
				UserResponse userResponse = UserResponse.convertUserToUserResponse(user);
				userResponse.setPassword("Fuck your mother, password are changed LOL!");
				return new ResponseEntity<>(userResponse, HttpStatus.ACCEPTED);
			} else
				return new ResponseEntity<>("We Are Sorry You aren't Active in our site", HttpStatus.BAD_REQUEST);
		}
		Employee employee = employeeService.findByEmailAndPassword(email, password).orElse(null);
		if (employee != null)
			return new ResponseEntity<>(employee, HttpStatus.ACCEPTED);

		return new ResponseEntity<>("Wrong In Email Or Password ", HttpStatus.UNAUTHORIZED);

	}

	public ResponseEntity<?> adminDashboardLogin(String email, String password) {
		if (email.equals("modernhomeinegypt@gmail.com") && password.equals("ModernHome"))
			return new ResponseEntity<>(Employee.admin(), HttpStatus.ACCEPTED);
		Employee employee = employeeService.findByEmailAndPassword(email, password).orElse(null);
		if (employee != null)
			return new ResponseEntity<>(employee, HttpStatus.ACCEPTED);
		return new ResponseEntity<>("Wrong In Email Or Password ", HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<?> forgetPassword(String email) {

		if (email.equals("modernhomeinegypt@gmail.com")) {
			authMailService.sendPasswordToEmail("modernhomeinegypt@gmail.com", "ModernHome");
			return sentPasswordSuccessfully();
		}
		User user = userService.findByEmail(email).orElse(null);
		if (user != null) {
			authMailService.sendPasswordToEmail(user.getEmail(), user.getPassword());
			return sentPasswordSuccessfully();
		}
		Employee employee = employeeService.findByEmail(email).orElse(null);
		if (employee != null) {
			authMailService.sendPasswordToEmail(employee.getEmail(), employee.getPassword());
			return sentPasswordSuccessfully();
		}
		return new ResponseEntity<>("This Email Aren't In Our Database!", HttpStatus.UNAUTHORIZED);

	}

	public ResponseEntity<?> sentPasswordSuccessfully() {
		return new ResponseEntity<>("The Password Send To Gmail Successfully", HttpStatus.ACCEPTED);
	}


	@SneakyThrows
	public Employee getLoggedInEmployee() {
		Optional<CustomUserDetails> optionalCustomUserDetails = getLoggedInUser();
		if (optionalCustomUserDetails.isPresent()) {
			CustomUserDetails customUserDetails = optionalCustomUserDetails.get();
			if (customUserDetails.getUserRole() == UserRole.ADMIN) {
				return Employee.admin();
			} else if (customUserDetails.getUserRole() == UserRole.EMPLOYEE) {
				return employeeService.findByEmail(customUserDetails.getEmail()).orElseThrow(
						() -> new Exception("Emp"));
			}

		}
		throw new Exception("Employee are not found");

	}

	private Optional<CustomUserDetails> getLoggedInUser() {
		// Get the currently logged-in user
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof CustomUserDetails) {
			CustomUserDetails customUserDetails = (CustomUserDetails) principal;
			return Optional.of(customUserDetails);
		}

		return Optional.empty();
	}

}
