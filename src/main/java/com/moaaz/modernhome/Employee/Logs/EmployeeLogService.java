package com.moaaz.modernhome.Employee.Logs;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeLogService {

     void saveLog(EmployeeLog employeeLog);
     List<EmployeeLogResponse>getAll();
}
