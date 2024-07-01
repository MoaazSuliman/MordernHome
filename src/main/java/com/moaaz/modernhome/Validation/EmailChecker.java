package com.moaaz.modernhome.Validation;

import com.moaaz.modernhome.Employee.Employee;
import com.moaaz.modernhome.Employee.EmployeeRepository;
import com.moaaz.modernhome.Employee.EmployeeServiceImp;
import com.moaaz.modernhome.User.User;
import com.moaaz.modernhome.User.UserService;

import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailChecker {

	@Autowired
	private UserService userService;
	@Autowired
	private EmployeeRepository employeeRepository;

	@SneakyThrows
	public void emailChecker(String email) {
		User user = userService.getUserByEmailWithoutException(email);
		if (user != null)
			throw new Exception("This Email Already In Our Database.");
		Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);
		if (employeeOptional.isPresent())
			throw new Exception("Email Already Exist");
	}

	public void test() {
		Map<String, String> map = new HashMap<>();
	}

}
