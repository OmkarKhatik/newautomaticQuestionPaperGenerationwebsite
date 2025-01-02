package com.newautomaticpapergenerationwebsite.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.newautomaticpapergenerationwebsite.model.LoginModel;

@Component
public class NewLoginRepo {

	 private static final List<LoginModel> LOGIN_MODELS = new ArrayList<>();
	 
	 public NewLoginRepo() {
		 LoginModel loginmodel = new LoginModel();
		 loginmodel.setId(1L);
		 loginmodel.setUsername("12345");
		 loginmodel.setPassword("12345");
		 loginmodel.setUserType("A");
		 
		 LOGIN_MODELS.add(loginmodel);
	 }
	 
	 public LoginModel adduser(LoginModel loginmodel) {
		 LOGIN_MODELS.add(loginmodel);
		 return loginmodel;
	 }
	 
	 public LoginModel findByUsername(String username) {
		 return LOGIN_MODELS.stream().filter(loginmodel -> loginmodel.getUsername() == username).findAny().orElse(null);
	 }
	 
	 
}
