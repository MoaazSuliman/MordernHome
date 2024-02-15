package com.moaaz.modernhome.User;

import com.moaaz.modernhome.Employee.Logs.EmployeeAction;
import com.moaaz.modernhome.Employee.Logs.EmployeeLog;
import com.moaaz.modernhome.Employee.Logs.EmployeeLogService;
import com.moaaz.modernhome.Employee.Logs.LogType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeLogService employeeLogService;
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
//        return new ResponseEntity<>(userService.login(email, password), HttpStatus.ACCEPTED);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }


    @PostMapping("/inActive/{userId}/employee/{employeeId}")
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> makeUserInActive(@PathVariable long userId , @PathVariable long employeeId) {
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.INACTIVE).logType(LogType.USER).build(), employeeId
        );
        userService.makeUserInActive(userId);
        return new ResponseEntity<>("User Are Not Active Now...", HttpStatus.OK);
    }
    @PostMapping("/active/{userId}/employee/{employeeId}")
    @Transactional(rollbackOn = Exception.class)
    public  ResponseEntity<?>makeUserActive(@PathVariable long userId , @PathVariable long employeeId){
        employeeLogService.saveLog(
                EmployeeLog.builder().employeeAction(EmployeeAction.ACTIVE).logType(LogType.USER).build(), employeeId
        );
        userService.makeUserActive(userId);
        return new ResponseEntity<>("User Are  Active Now...", HttpStatus.OK);
    }
    @GetMapping("/getUserByUsernameOrEmail")
    public ResponseEntity<?> getUsersByUsernameOrEmail(@RequestParam String username) {
        return new ResponseEntity<>(userService.getByNameOrEmail(username), HttpStatus.OK);
    }

}
