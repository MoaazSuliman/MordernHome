package com.moaaz.modernhome.Product;

import com.moaaz.modernhome.Employee.Logs.EmployeeAction;
import com.moaaz.modernhome.Employee.Logs.EmployeeLogService;
import com.moaaz.modernhome.Employee.Logs.LogType;
import com.moaaz.modernhome.events.EmployeeEvent;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductService productService;


	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	@EmployeeEvent(type = LogType.PRODUCT, action = EmployeeAction.ADD)
	public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest, @PathVariable Long employeeId) {

		return new ResponseEntity<>(productService.addProduct(productRequest), HttpStatus.CREATED);
	}

	@PutMapping("/{productId}")
	@EmployeeEvent(type = LogType.PRODUCT, action = EmployeeAction.UPDATE)
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable long productId) {

		return new ResponseEntity<>(productService.updateProduct(productRequest, productId), HttpStatus.OK);
	}

	@DeleteMapping("/{productId}")
	@Transactional(rollbackOn = Exception.class)
	@EmployeeEvent(type = LogType.PRODUCT, action = EmployeeAction.DELETE)
	public ResponseEntity<?> deleteProduct(@PathVariable long productId) {

		productService.deleteProduct(productId);
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(productService.getAll()
				, HttpStatus.OK);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<?> getById(@PathVariable long productId) {
		return new ResponseEntity<>(productService.getProductResponseById(productId), HttpStatus.OK);
	}

	@GetMapping("/search/{text}")
	public ResponseEntity<?> searchByText(@PathVariable String text) {
		return new ResponseEntity<>(productService.search(text), HttpStatus.OK);
	}


	@GetMapping("/getAllProductsForCategory/{categoryId}")
	public ResponseEntity<?> getAllProductsForCategory(@PathVariable long categoryId) {
		return new ResponseEntity<>(productService.getAllByCategoryId(categoryId), HttpStatus.OK);
	}
}
