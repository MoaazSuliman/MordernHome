package com.moaaz.modernhome.Category;

import com.moaaz.modernhome.Employee.Logs.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @Autowired
    private EmployeeLogService employeeLogService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(categoryServiceImp.getAll(), HttpStatus.OK);
    }

    @PostMapping("/employee/{employeeId}")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> add(@RequestBody @Valid CategoryRequest categoryRequest, @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.ADD).logType(LogType.CATEGORY).build(), employeeId
        );
        return new ResponseEntity<>(categoryServiceImp.add(categoryRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}/employee/{employeeId}")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> update(@RequestBody @Valid CategoryRequest categoryRequest, @PathVariable long categoryId, @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.UPDATE).logType(LogType.CATEGORY).build(), employeeId
        );
        return new ResponseEntity<>(categoryServiceImp.update(categoryRequest, categoryId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{categoryId}/employee/{employeeId}")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> deleteById(@PathVariable long categoryId, @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.UPDATE).logType(LogType.CATEGORY).build(), employeeId
        );
        categoryServiceImp.delete(categoryId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }


    @GetMapping("/search/{text}")
    public ResponseEntity<?> search(@PathVariable String text) {
        return new ResponseEntity<>(categoryServiceImp.search(text), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getById(@PathVariable long categoryId) {
        return new ResponseEntity<>(categoryServiceImp.getResponseById(categoryId), HttpStatus.OK);
    }

    @GetMapping("/getAllProducts/{categoryId}")
    public ResponseEntity<?> getAllProductsForCategory(@PathVariable long categoryId) {
        return new ResponseEntity<>(categoryServiceImp.getAllProductsForCategory(categoryId), HttpStatus.OK);
    }

}
