package com.moaaz.modernhome.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moaaz/api/modernhome/employees")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.addEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.updateEmployee(employee), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteById(@PathVariable long employeeId) {
        employeeService.delete(employeeId);
        return new ResponseEntity<>("Employee Are Deleted Successfully", HttpStatus.ACCEPTED);
    }


    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getById(@PathVariable long employeeId) {
        return new ResponseEntity<>(employeeService.getById(employeeId), HttpStatus.OK);
    }


}
