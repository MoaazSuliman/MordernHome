package com.moaaz.modernhome.Order;

import com.moaaz.modernhome.Employee.Logs.EmployeeAction;
import com.moaaz.modernhome.Employee.Logs.EmployeeLog;
import com.moaaz.modernhome.Employee.Logs.EmployeeLogService;
import com.moaaz.modernhome.Employee.Logs.LogType;
import com.moaaz.modernhome.Mail.OrderMailService;
import com.moaaz.modernhome.User.UserOrderService;

import com.moaaz.modernhome.events.EmployeeEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/orders")
@RestController
@CrossOrigin("*")
@Tag(name = "Orders")
public class OrderController {

	@Autowired
	private UserOrderService userOrderService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private EmployeeLogService employeeLogService;

	@Autowired
	private OrderMailService orderMailService;

	@Operation(description = "Adding New Order For The User")
	@PostMapping
	public ResponseEntity<?> addOrder(@RequestBody @Valid OrderRequest orderRequest) {
		orderMailService.notifyUser(orderService.addOrder(orderRequest));
		return new ResponseEntity<>("Order Added Successfully", HttpStatus.CREATED);
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<?> updateOrder(@RequestBody @Valid OrderRequest orderRequest, @PathVariable long orderId) {
		orderService.updateOrder(orderRequest, orderId);
		return new ResponseEntity<>("Order Updated Successfully", HttpStatus.ACCEPTED);
	}


	@PostMapping("/accept/{orderId}")
	@Transactional(rollbackOn = Exception.class)
	@EmployeeEvent(action = EmployeeAction.ACCEPT, type = LogType.ORDER)
	public ResponseEntity<?> acceptOrder(@PathVariable long orderId) {

		orderMailService.notifyUserOrderIsAccepted(orderService.acceptOrder(orderId));
		return new ResponseEntity<>("Accepted Successfully", HttpStatus.ACCEPTED);
	}

	@PostMapping("/complete/{orderId}")
	@Transactional(rollbackOn = Exception.class)
	@EmployeeEvent(type = LogType.ORDER, action = EmployeeAction.COMPLETE)
	public ResponseEntity<?> completeOrder(@PathVariable long orderId) {

		orderService.completeOrder(orderId);
		return new ResponseEntity<>("Completed Successfully", HttpStatus.ACCEPTED);

	}

	@PostMapping("/previousStatus/{orderId}")
	@Transactional(rollbackOn = Exception.class)
	@EmployeeEvent(type = LogType.ORDER, action = EmployeeAction.RETURN)
	public ResponseEntity<?> convertOrderStatusToPreviousStatus(@PathVariable long orderId) {
		orderService.getPreviousStatus(orderId);
		return new ResponseEntity<>("Done Successfully", HttpStatus.ACCEPTED);
	}


	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
	}

	@PostMapping("/test")
	public ResponseEntity<?> getAllBySearch(@RequestBody SearchRequest searchRequest) {
		System.out.println(searchRequest.toString());
		return new ResponseEntity<>(orderService.getAllOrdersFromDateToDateWithStatus(searchRequest), HttpStatus.OK);
	}

	@GetMapping("/getAllForUserByUserId/{userId}")
	public ResponseEntity<?> getAllForUserByUserId(@PathVariable long userId) {
		return new ResponseEntity<>(userOrderService.getAllOrdersForUser(userId), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOrderById(@PathVariable long id) {
		return new ResponseEntity<>(orderService.getById(id), HttpStatus.ACCEPTED);

	}
}
