package com.moaaz.modernhome.Order;

import com.moaaz.modernhome.Employee.Logs.EmployeeAction;
import com.moaaz.modernhome.Employee.Logs.EmployeeLog;
import com.moaaz.modernhome.Employee.Logs.EmployeeLogService;
import com.moaaz.modernhome.Employee.Logs.LogType;
import com.moaaz.modernhome.Mail.OrderMailService;
import com.moaaz.modernhome.User.UserOrderService;

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
//@Api(tags = "Order Controller"  , description = "This class have all endpoints for the orders.")

public class OrderController {

    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private EmployeeLogService employeeLogService;

    @Autowired
    private OrderMailService orderMailService;


    //    @ApiOperation(
//            value = "Adding Order ",
//            notes = "Adding Order for user by sending order request"
//    )
    @Operation(description = "Adding New Order For The User")
    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@RequestBody @Valid OrderRequest orderRequest) {
        orderMailService.notifyUser(orderService.addOrder(orderRequest));
        return new ResponseEntity<>("Order Added Successfully", HttpStatus.CREATED);
    }

    @PostMapping("/updateOrder/{orderId}")
    public ResponseEntity<?> updateOrder(@RequestBody @Valid OrderRequest orderRequest, @PathVariable long orderId) {
        orderService.updateOrder(orderRequest, orderId);
        return new ResponseEntity<>("Order Updated Successfully", HttpStatus.ACCEPTED);
    }


    @PostMapping("/accept/{orderId}/employee/{employeeId}")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> acceptOrder(@PathVariable long orderId  , @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.ACCEPT).logType(LogType.ORDER).build(), employeeId
        );
        orderMailService.notifyUserOrderIsAccepted(orderService.acceptOrder(orderId));
        return new ResponseEntity<>("Accepted Successfully", HttpStatus.ACCEPTED);
    }

    @PostMapping("/complete/{orderId}/employee/{employeeId}")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> completeOrder(@PathVariable long orderId  , @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.COMPLETE).logType(LogType.ORDER).build(), employeeId
        );
        orderService.completeOrder(orderId);
        return new ResponseEntity<>("Completed Successfully", HttpStatus.ACCEPTED);

    }

    @PostMapping("/previousStatus/{orderId}/employee/{employeeId}")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> convertOrderStatusToPreviousStatus(@PathVariable long orderId , @PathVariable Long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.RETURN).logType(LogType.ORDER).build(), employeeId
        );
        orderService.getPreviousStatus(orderId);

        return new ResponseEntity<>("Done Successfully", HttpStatus.ACCEPTED);
    }


    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/test")
    public ResponseEntity<?> getAllBySearch(@RequestBody SearchRequest searchRequest) {
        log.info("This is the search ", searchRequest);
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
