package com.moaaz.modernhome.Employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moaaz.modernhome.User.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {


	@NotNull(message = "name must not be null")
	@NotEmpty(message = "name must not be empty")
	private String name;

	@NotNull(message = "email must not be null")
	@NotEmpty(message = "email must not be empty")
	@Email(message = "not valid email")
	private String email;

	private String phone1;

	private String phone2;

	private String address;


	@NotNull(message = "password must not be null")
	@NotEmpty(message = "password must not be empty")
	@Size(max = 20, min = 8, message = "password Must Be Between 8 and 20 character.")
	protected String password;

}
