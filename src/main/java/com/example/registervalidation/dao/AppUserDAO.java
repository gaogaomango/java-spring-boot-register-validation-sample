package com.example.registervalidation.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.registervalidation.form.AppUserForm;
import com.example.registervalidation.model.AppUser;
import com.example.registervalidation.model.Gender;

@Repository
public class AppUserDAO {
	
	// Config in WebSecurityConfig
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final Map<Long, AppUser> USERS_MAP = new HashMap<>();
	
	static {
		initData();
	}
	
	private static void initData() {
		String encrytedPassword = "";
		
		AppUser tom = new AppUser(1L, "tom", "Tom", "Tom", true, Gender.MALE, "tom@example.com", encrytedPassword, "US");
		AppUser masa = new AppUser(2L, "masa", "Masa", "Masa", true, Gender.MALE, "masa@example.com", encrytedPassword, "JA");
		AppUser alice = new AppUser(3L, "alice", "Alice", "Alice", true, Gender.FEMALE, "alice@example.com", encrytedPassword, "US");
		
		USERS_MAP.put(tom.getUserId(), tom);
		USERS_MAP.put(masa.getUserId(), masa);
		USERS_MAP.put(alice.getUserId(), alice);
	}
	
	public Long getMaxUserId() {
		long max = 0;
		for(Long id : USERS_MAP.keySet()) {
			if(id > max) {
				max = id;
			}
		}
		return max;
	}
	
	public AppUser findAppUserByUserName(String userName) {
		Collection<AppUser> appUsers = USERS_MAP.values();
		for(AppUser u : appUsers) {
			if(u.getUserName().equals(userName)) {
				return u;
			}
		}
		return null;
	}

	public AppUser findAppUserByUserEMail(String email) {
		Collection<AppUser> appUsers = USERS_MAP.values();
		for(AppUser u : appUsers) {
			if(u.getEmail().equals(email)) {
				return u;
			}
		}
		return null;
	}

	public List<AppUser> getAppUsers() {
		List<AppUser> list = new ArrayList<>();
		
		System.out.println();
		
		list.addAll(USERS_MAP.values());
		return list;
	}
	
	public AppUser createAppUser(AppUserForm form) {
		Long userId = this.getMaxUserId();
		String encrytedPassword = this.passwordEncoder.encode(form.getPassword());
		
		AppUser user = new AppUser(userId
				, form.getUserName()
				, form.getFirstName()
				, form.getLastName()
				, false
				, form.getGender()
				, form.getEmail()
				, form.getCountryCode()
				, encrytedPassword);
		USERS_MAP.put(userId, user);

		return user;
	}

}
