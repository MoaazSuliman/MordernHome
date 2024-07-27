package com.moaaz.modernhome.Category;

import com.moaaz.modernhome.Employee.Logs.EmployeeAction;
import com.moaaz.modernhome.Employee.Logs.LogType;
import com.moaaz.modernhome.events.EmployeeEvent;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

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

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {

	@Autowired
	private CategoryServiceImp categoryServiceImp;


	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(categoryServiceImp.getAll(), HttpStatus.OK);
	}

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	@EmployeeEvent(type = LogType.CATEGORY, action = EmployeeAction.ADD)
	public ResponseEntity<?> add(@RequestBody @Valid CategoryRequest categoryRequest) {

		return new ResponseEntity<>(categoryServiceImp.add(categoryRequest), HttpStatus.CREATED);
	}

	@DeleteMapping("/{categoryId}")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> deleteById(@PathVariable long categoryId) {
//        employeeLogService.saveLog(
//                EmployeeLog.builder().employeeAction(EmployeeAction.UPDATE).logType(LogType.CATEGORY).build(), employeeId
//        );
		categoryServiceImp.delete(categoryId);
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}

	@PutMapping("/{categoryId}")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> update(@RequestBody @Valid CategoryRequest categoryRequest, @PathVariable long categoryId) {

		return new ResponseEntity<>(categoryServiceImp.update(categoryRequest, categoryId), HttpStatus.ACCEPTED);
	}


	@GetMapping("/search/{text}")
	public ResponseEntity<?> search(@PathVariable String text) {
		return new ResponseEntity<>(categoryServiceImp.search(text), HttpStatus.OK);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<?> getById(@PathVariable long categoryId) {
		return new ResponseEntity<>(categoryServiceImp.getResponseById(categoryId), HttpStatus.OK);
	}


}
