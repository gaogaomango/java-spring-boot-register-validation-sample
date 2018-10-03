package com.example.registervalidation.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.registervalidation.dao.AppUserDAO;
import com.example.registervalidation.form.AppUserForm;
import com.example.registervalidation.model.AppUser;

@Component
public class AppUserValidator implements Validator {

	private EmailValidator emailValidator = EmailValidator.getInstance();
	
	@Autowired
	private AppUserDAO appUserDAO;
	
	@Override
	public boolean supports(Class<?> clazz) {		
		return clazz == AppUserForm.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		AppUserForm appUserForm = (AppUserForm) target;
		

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.appUserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.appUserForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.appUserForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.appUserForm.confirmPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "NotEmpty.appUserForm.gender");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "countryCode", "NotEmpty.appUserForm.countryCode");

        if(!this.emailValidator.isValid(appUserForm.getEmail())) {
        	// invalid email
        	errors.rejectValue("email", "Pattern.appUserForm.email");
        } else if (appUserForm.getUserId() == null) {
        	AppUser dbUser = appUserDAO.findAppUserByUserEMail(appUserForm.getEmail());
        	if(dbUser != null) {
        		// Emial has been used by another account.
        		errors.rejectValue("email",  "Duplicate.appUserForm.email");
        	}
        }
        
        if(!errors.hasFieldErrors("userName")) {
        	AppUser dbUser = appUserDAO.findAppUserByUserName(appUserForm.getUserName());
        	if(dbUser != null) {
        		// Username is not available.
        		errors.rejectValue("userName", "Duplicate.appUserForm.userName");
        	}
        }
        
        if(!errors.hasErrors()) {
        	if(!appUserForm.getConfirmPassword().equals(appUserForm.getConfirmPassword())) {
        		errors.rejectValue("confirmPassword",  "Match.appUserForm.confirmPassword");
        	}
        }
        
	}

	
	
}
