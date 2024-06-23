package com.moaaz.modernhome.Validation;

import com.moaaz.modernhome.Employee.EmployeeService;
import com.moaaz.modernhome.User.User;
import com.moaaz.modernhome.User.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

@Service
public class EmailChecker {

    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    @SneakyThrows
    public void emailChecker(String email) {
        User user = userService.getUserByEmailWithoutException(email);
        if (user != null)
            throw new Exception("This Email Already In Our Database.");
        employeeService.checkEmailIfExistToThrowException(email);
    }

    public void test() {
        Map<String , String>map = new HashMap<>();
    }

}
