package com.moaaz.modernhome.Employee.Logs;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moaaz.modernhome.Employee.Employee;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeeLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JsonIgnore
	private Employee employee;

	@Transient
	private String employeeEmail;

	@Enumerated(EnumType.STRING)
	private EmployeeAction employeeAction;

	@Enumerated(EnumType.STRING)
	private LogType logType;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime creationDate;

	private boolean isAdmin;

	public String getEmployeeEmail() {
		return this.employee.getEmail();
	}
}
