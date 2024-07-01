package com.moaaz.modernhome.security;


import com.moaaz.modernhome.Employee.Employee;
import com.moaaz.modernhome.Employee.EmployeeRepository;
import com.moaaz.modernhome.User.User;
import com.moaaz.modernhome.User.UserRepository;
import com.moaaz.modernhome.security.enums.UserRole;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final EmployeeRepository employeeRepository;
	private final UserRepository userRepository;
	private final CustomUserService customUserService;
	private final String USER_ROLE_PREFIX = "ROLE_";
	@Autowired
	private Admin admin;
	@Autowired
	private Developer developer;


	@Override
	public UserDetails loadUserByUsername(String username) {


		Optional<Admin> optionalAdmin = customUserService.findAdminByEmail(username);
		if (optionalAdmin.isPresent()) {

			CustomUserDetails customAdmin = CustomUserDetails
					.builder()
					.id(-1)
					.email(admin.email)
					.password(admin.password)
					.userRole(UserRole.ADMIN)
					.build();
			customAdmin.addAuthority(USER_ROLE_PREFIX + UserRole.ADMIN);
			return customAdmin;
		}


		Optional<Employee> employeeOptional = employeeRepository.findByEmail(username);
		if (employeeOptional.isPresent()) {

			Employee employee = employeeOptional.get();

			CustomUserDetails customEmployee = CustomUserDetails.builder()
					.id(employee.getId())
					.email(employee.getEmail())
					.password(employee.getPassword())
					.userRole(UserRole.EMPLOYEE)
					.build();
			customEmployee.addAuthority(USER_ROLE_PREFIX + UserRole.EMPLOYEE);
			return customEmployee;

		}
		Optional<User> userOptional = userRepository.findByEmail(username);
		if (userOptional.isPresent()) {

			User user = userOptional.get();

			CustomUserDetails customUser = CustomUserDetails.builder()
					.id(user.getId())
					.email(user.getEmail())
					.password(user.getPassword())
					.userRole(UserRole.USER)
					.build();
			customUser.addAuthority(USER_ROLE_PREFIX + UserRole.USER);
			return customUser;

		}


		throw new UsernameNotFoundException("Could not find user");

	}

}

