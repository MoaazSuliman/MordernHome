package com.moaaz.modernhome.Category;

import com.moaaz.modernhome.Employee.Logs.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moaaz/api/modernhome/categories")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EmployeeLogService employeeLogService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<?> add(@RequestBody @Valid Category category, @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.ADD).logType(LogType.CATEGORY).build(), employeeId
        );
        return new ResponseEntity<>(categoryService.addCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}/employee/{employeeId}")
    public ResponseEntity<?> update(@RequestBody @Valid Category category, @PathVariable long categoryId, @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.UPDATE).logType(LogType.CATEGORY).build(), employeeId
        );
        return new ResponseEntity<>(categoryService.updateCategory(category, categoryId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{categoryId}/employee/{employeeId}")
    public ResponseEntity<?> deleteById(@PathVariable long categoryId, @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.UPDATE).logType(LogType.CATEGORY).build(), employeeId
        );
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }


    @GetMapping("/search/{text}")
    public ResponseEntity<?> search(@PathVariable String text) {
        return new ResponseEntity<>(categoryService.search(text), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getById(@PathVariable long categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryResponseById(categoryId), HttpStatus.OK);
    }

    @GetMapping("/getAllProducts/{categoryId}")
    public ResponseEntity<?> getAllProductsForCategory(@PathVariable long categoryId) {
        return new ResponseEntity<>(categoryService.getAllProductsForCategory(categoryId), HttpStatus.OK);
    }

}
