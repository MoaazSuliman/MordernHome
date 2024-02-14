package com.moaaz.modernhome.Employee.Logs;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeLogService {

    public void saveLog(EmployeeLog employeeLog , Long employeeId);
    public List<EmployeeLog>getAll();
}
