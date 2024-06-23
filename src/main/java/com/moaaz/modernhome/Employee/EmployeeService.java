package com.moaaz.modernhome.Employee;

import com.moaaz.modernhome.User.Role;
import com.moaaz.modernhome.Validation.EmailChecker;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmailChecker emailChecker;

    public Employee addEmployee(Employee employee) {
        emailChecker.emailChecker(employee.getEmail());
        checkEmailIfExistToThrowException(employee.getEmail());
        employee.setRole(Role.EMPLOYEE);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        Employee existingEmployee = getById(employee.getId());
        if(!employee.getEmail().equals(existingEmployee.getEmail()))
            emailChecker.emailChecker(employee .getEmail());
        employee.setRole(Role.EMPLOYEE);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public void delete(long id) {
        getById(id);
        employeeRepository.deleteById(id);
    }

    @SneakyThrows
    public void checkEmailIfExistToThrowException(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElse(null);
        if (employee != null)
            throw new Exception("This Email Already In Our Database");
    }

    public Employee getById(long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("There Are No Emp With Id Equals To " + id)
        );
    }

}
