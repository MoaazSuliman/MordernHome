package com.moaaz.modernhome.Employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moaaz.modernhome.Employee.Logs.EmployeeLog;
import com.moaaz.modernhome.User.Person;
import com.moaaz.modernhome.security.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee extends Person {

	@OneToMany(mappedBy = "employee")
	@JsonIgnore
	private List<EmployeeLog> employeeLogs;


	public static Employee admin() {
		Employee employee = new Employee();
		employee.setId(-1L);
		employee.setName("Admin");
		employee.setEmail("modernhomeinegypt@gmail.com");
		employee.setRole(UserRole.ADMIN);
		return employee;
	}
}
