package com.moaaz.modernhome.Employee.Logs;

import com.moaaz.modernhome.Employee.Employee;
import com.moaaz.modernhome.Employee.EmployeeServiceImp;
import com.moaaz.modernhome.security.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeLogServiceImp implements EmployeeLogService {

	@Autowired
	private EmployeeLogRepository employeeLogRepository;

	@Autowired
	private EmployeeServiceImp employeeServiceImp;

	@Autowired
	private EmployeeLogMapper employeeLogMapper;


	@Override
	public void saveLog(EmployeeLog employeeLog) {
		Employee employee = employeeLog.getEmployee();
		if (UserRole.ADMIN.equals(employee.getRole()))
			return;
		employeeServiceImp.getById(employee.getId());
		employeeLog.setCreationDate(LocalDateTime.now());
		employeeLogRepository.save(employeeLog);
	}

	@Override
	public List<EmployeeLogResponse> getAll() {
		return employeeLogRepository
				.findAll()
				.stream()
				.map(employeeLogMapper::toResponse)
				.collect(Collectors.toList());
	}
}
