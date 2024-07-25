package com.moaaz.modernhome.Product;

import com.moaaz.modernhome.Employee.Logs.EmployeeAction;
import com.moaaz.modernhome.Employee.Logs.LogType;
import com.moaaz.modernhome.Product.service.ProductServiceImp;
import com.moaaz.modernhome.events.EmployeeEvent;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductServiceImp productServiceImp;


	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	@EmployeeEvent(type = LogType.PRODUCT, action = EmployeeAction.ADD)
	public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest) {

		return new ResponseEntity<>(productServiceImp.addProduct(productRequest), HttpStatus.CREATED);
	}

	@PutMapping("/{productId}")
	@EmployeeEvent(type = LogType.PRODUCT, action = EmployeeAction.UPDATE)
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable long productId) {

		return new ResponseEntity<>(productServiceImp.updateProduct(productRequest, productId), HttpStatus.OK);
	}

	@DeleteMapping("/{productId}")
	@Transactional(rollbackOn = Exception.class)
	@EmployeeEvent(type = LogType.PRODUCT, action = EmployeeAction.DELETE)
	public ResponseEntity<?> deleteProduct(@PathVariable long productId) {

		productServiceImp.deleteProduct(productId);
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(productServiceImp.getAll()
				, HttpStatus.OK);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<?> getById(@PathVariable long productId) {
		return new ResponseEntity<>(productServiceImp.getProductResponseById(productId), HttpStatus.OK);
	}

	@GetMapping("/search/{text}")
	public ResponseEntity<?> searchByText(@PathVariable String text) {
		return new ResponseEntity<>(productServiceImp.search(text), HttpStatus.OK);
	}


	@GetMapping("/getAllProductsForCategory/{categoryId}")
	public ResponseEntity<?> getAllProductsForCategory(@PathVariable long categoryId) {
		return new ResponseEntity<>(productServiceImp.getAllByCategoryId(categoryId), HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody @Valid ProductSearch productSearch, @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int size) {
		Page<ProductResponse> search = productServiceImp.getAll(productSearch, PageRequest.of(page, size));
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(search.getTotalElements()));
		return new ResponseEntity<>(search.getContent(), headers, HttpStatus.OK);

	}
}
