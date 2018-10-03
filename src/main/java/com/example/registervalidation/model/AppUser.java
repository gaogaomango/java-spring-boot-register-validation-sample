package com.example.registervalidation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
	
	private Long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private boolean enabled;
	private String gender;
	private String email;
	private String encryptedPassword;
	private String countryCode;

}
