package com.moaaz.modernhome.Employee;

import com.moaaz.modernhome.Validation.EmailChecker;
import com.moaaz.modernhome.security.enums.UserRole;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {


	private final EmployeeRepository employeeRepository;
	private final EmailChecker emailChecker;
	private final EmployeeMapper employeeMapper;


	@Override
	public EmployeeResponse add(EmployeeRequest request) {
		emailChecker.emailChecker(request.getEmail());
		checkEmailIfExistToThrowException(request.getEmail());
		Employee employee = employeeMapper.toEntity(request);
		employee.setRole(UserRole.EMPLOYEE);
		return employeeMapper.toResponse(employeeRepository.save(employee));
	}

	@Override
	public EmployeeResponse update(EmployeeRequest request, Long id) {
		Employee existingEmployee = getById(id);
		if (!request.getEmail().equals(existingEmployee.getEmail()))
			emailChecker.emailChecker(request.getEmail());

		existingEmployee.setName(request.getName());
		existingEmployee.setEmail(request.getEmail());
		existingEmployee.setPassword(request.getPassword());
		existingEmployee.setAddress(request.getAddress());
		existingEmployee.setPhone1(request.getPhone1());
		existingEmployee.setPhone2(request.getPhone2());


		return employeeMapper.toResponse(employeeRepository.save(existingEmployee));
	}

	@Override
	public List<EmployeeResponse> getAll() {
		return employeeRepository.findAllByActiveIsTrue()
				.stream()
				.map(employeeMapper::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		Employee employee = getById(id);
		employee.setActive(false);
		employeeRepository.save(employee);

	}


	public void checkEmailIfExistToThrowException(String email) {
		employeeRepository.findByEmail(email)
				.orElseThrow(() -> new NoSuchElementException("This Email Already In Our Database"));

	}

	public Employee getById(Long id) {
		return employeeRepository.findById(id).orElseThrow(
				() -> new NoSuchElementException("There Are No Emp With Id Equals To " + id)
		);
	}

	public EmployeeResponse getResponseById(Long id) {
		Employee byId = getById(id);
		return employeeMapper.toResponse(byId);
	}

}
