package com.moaaz.modernhome.User;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

	protected String name;
	protected String email;
	protected String phone1;
	protected String phone2;
	protected String address;
	protected String password;
}
