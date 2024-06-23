package com.moaaz.modernhome.Employee;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImp employeeServiceImp;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(employeeServiceImp.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return new ResponseEntity<>(employeeServiceImp.add(employeeRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<?> update(@RequestBody EmployeeRequest employeeRequest  , @PathVariable Long employeeId) {
        return new ResponseEntity<>(employeeServiceImp.update(employeeRequest , employeeId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteById(@PathVariable Long employeeId) {
        employeeServiceImp.delete(employeeId);
        return new ResponseEntity<>("Employee Are Deleted Successfully", HttpStatus.ACCEPTED);
    }


    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getById(@PathVariable long employeeId) {
        return new ResponseEntity<>(employeeServiceImp.getResponseById(employeeId), HttpStatus.OK);
    }
}
