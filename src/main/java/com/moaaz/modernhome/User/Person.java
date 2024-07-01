package com.moaaz.modernhome.User;

import com.moaaz.modernhome.Employee.Employee;
import com.moaaz.modernhome.security.enums.UserRole;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass

public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	protected String name;

	protected String email;
	protected String phone1;
	protected String phone2;
	protected String address;
	protected String password;

	@Enumerated(EnumType.STRING)
	protected UserRole role;


}
