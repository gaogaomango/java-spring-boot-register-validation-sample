package com.example.registervalidation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.registervalidation.dao.AppUserDAO;
import com.example.registervalidation.dao.CountryDAO;
import com.example.registervalidation.form.AppUserForm;
import com.example.registervalidation.model.AppUser;
import com.example.registervalidation.model.Country;
import com.example.registervalidation.validator.AppUserValidator;

@Controller
public class MainController {
	
	@Autowired
	private AppUserDAO appUserDAO;
	
	@Autowired
	private CountryDAO countryDAO;
	
	@Autowired
	private AppUserValidator appUserValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		// Form target
		Object target = dataBinder.getTarget();
		if(target == null) {
			return;
		}
		System.out.println("Target=" + target);
		
		if(target.getClass() == AppUserForm.class) {
			dataBinder.setValidator(appUserValidator);
		}
	}
	
	@RequestMapping("/")
	public String viewHome(Model model) {
		return "welcomePage";
	}
	
	@RequestMapping("/members")
	public String viewMembers(Model model) {
		
		List<AppUser> list = appUserDAO.getAppUsers();
		System.out.println("_members :" + list.toString());
		model.addAttribute("members", list);
				
		return "membersPage";
	}
	
	@RequestMapping("/registerSuccessful")
	public String viewRegsterSuccessful(Model model) {
		
		return "registerSuccessfulPage";
	}
	
	// Show Register page.
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String viewRegister(Model model) {
		
		AppUserForm form = new AppUserForm();
		List<Country> countries = countryDAO.getCountries();
		
		model.addAttribute("appUserForm", form);
		model.addAttribute("countries", countries);
		
		return "registerPage";
	}
	
	// This method is called to save the registration information.
	// @Validated: To ensure that this Form
	// has been Validated before this method is invoked.
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String saveRegister(Model model
			, @ModelAttribute("appUserForm") @Validated AppUserForm appUserForm
			, BindingResult result
			, final RedirectAttributes redirectAttributes) {
		
		// Validate result
		if(result.hasErrors()) {
			List<Country> countries = countryDAO.getCountries();
			model.addAttribute("countries", countries);
			return "registerPage";
		}
		AppUser newUser = null;
		try {
			newUser = appUserDAO.createAppUser(appUserForm);
		} catch (Exception e) {
			List<Country> countries = countryDAO.getCountries();
			model.addAttribute("countries", countries);
			model.addAttribute("errorMessage", "Error:" + e.getMessage());
			return "registerPage";
		}
		
		redirectAttributes.addFlashAttribute("flashUser", newUser);
		
		return "redirect:/registerSuccessful";
	}

}
