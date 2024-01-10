package com.moaaz.modernhome.User;


import com.moaaz.modernhome.Validation.EmailChecker;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private EmailChecker emailChecker;

//    public User getUserByEmail(String email) {
//        return userRepository.getUserByEmail(email).orElseThrow(
//                () -> new NoSuchElementException("There Are No User With Email = " + email)
//        );
//    }

    public User getUserByEmailWithoutException(String email) {
        return userRepository.getUserByEmail(email).orElse(null);
    }

//    public User addUserByEmail(String email) {
//        User user = new User();
//        user.setEmail(email);
//        user.setCart(new Cart());
//
//        return userRepository.save(user);
//    }


    public UserResponse register(User user) {
        log.info("Register Function In The User");
//        checkIfEmailAreExistingToThrowException(user.getEmail());
        emailChecker.emailChecker(user.getEmail());
        user.setRole(Role.USER);
        return UserResponse.convertUserToUserResponse(userRepository.save(user));
    }


    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("There Are No User WIth Id= " + userId)
        );
    }

    public User updateUser(User user, long userId) {
        User existingUser = getUserById(userId);
        if (!user.getEmail().equals(existingUser.getEmail()))
            emailChecker.emailChecker(user.getEmail());
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAddress(user.getAddress());
        existingUser.setPhone1(user.getPhone1());
        existingUser.setPhone2(user.getPhone2());

        return userRepository.save(existingUser);
    }

//    public User addEmployee(User user) {
//        user.setRole(Role.EMPLOYEE);
//        return userRepository.save(user);
//    }

    @SneakyThrows
    public void checkIfEmailAreExistingToThrowException(String email) {
        log.info("Here In Email Checker!");
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null)
            throw new NoSuchElementException("This Email Already In Our Database!");
        log.info("User Aren't Equal To Null");

    }

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(UserResponse::convertUserToUserResponse).toList();
    }

}
