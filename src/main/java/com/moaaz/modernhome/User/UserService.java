package com.moaaz.modernhome.User;

import java.util.List;

public interface UserService {

	User getUserByEmailWithoutException(String email);
	UserResponse register(UserRequest userRequest);
	User getUserById(long userId);
	User updateUser(UserRequest userRequest, long userId);
	void checkIfEmailAreExistingToThrowException(String email);
	List<UserResponse> getAll();
	void makeUserActive(long userId);
	void makeUserInActive(long id);
	List<UserResponse> getByNameOrEmail(String text);
}
