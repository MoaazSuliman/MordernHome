package com.moaaz.modernhome.Employee;

import com.moaaz.modernhome.User.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

	private long id;
	private String name;
	private String email;
	private String phone1;
	private String phone2;
	private String address;
	protected String password;
	private Role role;
}
