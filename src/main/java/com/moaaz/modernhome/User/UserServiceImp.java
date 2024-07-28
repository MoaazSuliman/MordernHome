package com.moaaz.modernhome.User;


import com.moaaz.modernhome.Validation.EmailChecker;
import com.moaaz.modernhome.security.enums.UserRole;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

	private final UserRepository userRepository;

	private final EmailChecker emailChecker;

	private final UserMapper userMapper;

	@Override
	public User getUserByEmailWithoutException(String email) {
		return userRepository.getUserByEmail(email).orElse(null);
	}


	@Override
	public UserResponse register(UserRequest userRequest) {
		checkIfEmailAreExistingToThrowException(userRequest.getEmail());

		User user = userMapper.toEntity(userRequest);
		emailChecker.emailChecker(user.getEmail());
		user.setRole(UserRole.USER);
		user.setActive(true);

		return userMapper.toResponse(userRepository.save(user));
	}


	@Override
	public User getUserById(long userId) {
		return userRepository.findById(userId).orElseThrow(
				() -> new NoSuchElementException("There Are No User WIth Id= " + userId)
		);
	}

	@Override
	public User updateUser(UserRequest userRequest, long userId) {
		User existingUser = getUserById(userId);
		if (!userRequest.getEmail().equals(existingUser.getEmail()))
			emailChecker.emailChecker(userRequest.getEmail());
		existingUser.setName(userRequest.getName());
		existingUser.setEmail(userRequest.getEmail());
		existingUser.setAddress(userRequest.getAddress());
		existingUser.setPhone1(userRequest.getPhone1());
		existingUser.setPhone2(userRequest.getPhone2());

		return userRepository.save(existingUser);
	}


	@SneakyThrows
	@Override
	public void checkIfEmailAreExistingToThrowException(String email) {
		userRepository.findByEmail(email)
				.orElseThrow(() -> new NoSuchElementException("This Email Already In Our Database!"));
	}

	@Override
	public List<UserResponse> getAll() {
		return userRepository.findAll()
				.stream()
				.map(userMapper::toResponse)
				.toList();
	}


	@Override
	public void makeUserActive(long userId) {
		User user = getUserById(userId);
		user.setActive(true);
		userRepository.save(user);
	}

	@Override
	public void makeUserInActive(long id) {
		User user = getUserById(id);
		user.setActive(false);
		userRepository.save(user);
	}

	@Override
	public List<UserResponse> getByNameOrEmail(String text) {
		return userRepository.findAllByNameContainsOrEmailContains(text, text)
				.stream()
				.map(userMapper::toResponse).collect(Collectors.toList());
	}


}
