package com.moaaz.modernhome.Employee.Logs;

import com.moaaz.modernhome.Employee.Employee;
import com.moaaz.modernhome.Employee.EmployeeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeLogServiceImp implements EmployeeLogService {

    @Autowired
    private EmployeeLogRepository employeeLogRepository;

    @Autowired
    private EmployeeServiceImp employeeServiceImp;

    @Autowired
    private EmployeeLogMapper employeeLogMapper;


    @Override
    public void saveLog(EmployeeLog employeeLog, Long employeeId) {
        if (employeeId == 0)
            return;
        Employee employee = employeeServiceImp.getById(employeeId);
        employeeLog.setEmployee(employee);

        employeeLog.setCreationDate(LocalDateTime.now());
        employeeLogRepository.save(employeeLog);
    }

    @Override
    public List<EmployeeLogResponse> getAll() {
        return employeeLogRepository.findAll().stream().map(employeeLogMapper::toResponse).collect(Collectors.toList());
    }
}
