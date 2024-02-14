package com.moaaz.modernhome.Employee.Logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/moaaz/api/modernhome/employeeLogs")
@RestController
public class EmployeeLogController {

    @Autowired
    private EmployeeLogService employeeLogService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(employeeLogService.getAll(), HttpStatus.OK);
    }
}
