package com.moaaz.modernhome.security;

import com.moaaz.modernhome.security.enums.UserRole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomUserDetails implements UserDetails {
	private long id;
	private String email;
	private String password;
	private UserRole userRole;
	private List<String> userAuthorities = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
		getUserAuthorities().forEach(userAuthority -> {
			simpleGrantedAuthorities.add(new SimpleGrantedAuthority(userAuthority));
		});
		return simpleGrantedAuthorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public List<String> getUserAuthorities() {
		return this.userAuthorities == null ? new ArrayList<>() : userAuthorities;
	}

	public void addAuthority(String authority) {
		if (this.userAuthorities == null)
			this.userAuthorities = new ArrayList<>();
		this.userAuthorities.add(authority);
	}
}
