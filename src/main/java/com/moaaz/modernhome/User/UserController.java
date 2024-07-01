package com.moaaz.modernhome.User;

import com.moaaz.modernhome.Employee.Logs.EmployeeAction;
import com.moaaz.modernhome.Employee.Logs.LogType;
import com.moaaz.modernhome.events.EmployeeEvent;
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


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.register(userRequest), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }


    @PostMapping("/inActive/{userId}")
    @Transactional(rollbackOn = Exception.class)
    @EmployeeEvent(type = LogType.USER, action = EmployeeAction.INACTIVE)
    public ResponseEntity<?> makeUserInActive(@PathVariable long userId) {

        userService.makeUserInActive(userId);
        return new ResponseEntity<>("User Are Not Active Now...", HttpStatus.OK);
    }
    @PostMapping("/active/{userId}")
    @Transactional(rollbackOn = Exception.class)
    @EmployeeEvent(type = LogType.USER, action = EmployeeAction.ACTIVE)
    public  ResponseEntity<?>makeUserActive(@PathVariable long userId ){

        userService.makeUserActive(userId);
        return new ResponseEntity<>("User Are  Active Now...", HttpStatus.OK);
    }
    @GetMapping("/getUserByUsernameOrEmail")
    public ResponseEntity<?> getUsersByUsernameOrEmail(@RequestParam String username) {
        return new ResponseEntity<>(userService.getByNameOrEmail(username), HttpStatus.OK);
    }

}
