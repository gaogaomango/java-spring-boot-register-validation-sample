package com.example.registervalidation.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserForm {
	private Long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private boolean enabled;
	private String gender;
	private String email;
	private String password;
	private String confirmPassword;
	private String countryCode;
	

}
