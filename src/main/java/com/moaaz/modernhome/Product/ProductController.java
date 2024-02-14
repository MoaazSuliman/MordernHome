package com.moaaz.modernhome.Product;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.moaaz.modernhome.Employee.Logs.EmployeeAction;
import com.moaaz.modernhome.Employee.Logs.EmployeeLog;
import com.moaaz.modernhome.Employee.Logs.EmployeeLogService;
import com.moaaz.modernhome.Employee.Logs.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/moaaz/api/modernhome/products")
@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private EmployeeLogService employeeLogService;

    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest, @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.ADD).logType(LogType.PRODUCT).build(), employeeId
        );
        return new ResponseEntity<>(productService.addProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}/employee/{employeeId}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable long productId, @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.UPDATE).logType(LogType.PRODUCT).build(), employeeId
        );
        return new ResponseEntity<>(productService.updateProduct(productRequest, productId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}/employee/{employeeId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long productId , @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.DELETE).logType(LogType.PRODUCT).build(), employeeId
        );
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(productService.getAll()
                , HttpStatus.OK);
    }

    @GetMapping("/getById/{productId}")
    public ResponseEntity<?> getById(@PathVariable long productId) {
        return new ResponseEntity<>(productService.getProductResponseById(productId), HttpStatus.OK);
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<?> searchByText(@PathVariable String text) {
        return new ResponseEntity<>(productService.search(text), HttpStatus.OK);
    }


    @GetMapping("/getAllProducts/{categoryId}")
    public ResponseEntity<?> getAllProductsForCategory(@PathVariable long categoryId) {
        return new ResponseEntity<>(productService.getAllByCategoryId(categoryId), HttpStatus.OK);
    }
}
