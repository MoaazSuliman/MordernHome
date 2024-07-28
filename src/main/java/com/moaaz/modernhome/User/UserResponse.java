package com.moaaz.modernhome.User;

import com.moaaz.modernhome.Order.Order;
import com.moaaz.modernhome.Order.OrderResponse;
import com.moaaz.modernhome.Order.OrderStatus;
import com.moaaz.modernhome.security.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private long id;
	private String name;

	private String email;
	private String phone1;
	private String phone2;
	private String address;
	private String password;
	private boolean active;
	private UserRole role;


}
