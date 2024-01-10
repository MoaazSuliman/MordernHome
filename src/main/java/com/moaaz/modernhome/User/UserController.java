package com.moaaz.modernhome.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("moaaz/api/modernhome/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
//        return new ResponseEntity<>(userService.login(email, password), HttpStatus.ACCEPTED);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<?>getAll(){
        return new ResponseEntity<>(userService.getAll() , HttpStatus.OK);
    }


}
