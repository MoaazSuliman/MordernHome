package com.moaaz.modernhome.Employee.Logs;

import com.moaaz.modernhome.Employee.Employee;
import com.moaaz.modernhome.Employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeLogServiceImp implements EmployeeLogService {

    @Autowired
    private EmployeeLogRepository employeeLogRepository;

    @Autowired
    private EmployeeService employeeService;


    @Override
    public void saveLog(EmployeeLog employeeLog, Long employeeId) {
        if (employeeId != 0) {
            Employee employee = employeeService.getById(employeeId);
            employeeLog.setEmployee(employee);
        } else
            employeeLog.setAdmin(true);
        employeeLog.setCreationDate(LocalDateTime.now());
        employeeLogRepository.save(employeeLog);
    }

    @Override
    public List<EmployeeLog> getAll() {
        return employeeLogRepository.findAll();
    }
}
