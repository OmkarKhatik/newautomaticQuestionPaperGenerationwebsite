package com.newautomaticpapergenerationwebsite.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newautomaticpapergenerationwebsite.model.LoginModel;
import com.newautomaticpapergenerationwebsite.repo.LoginRepo;
@Service
public class LoginService {
	@Autowired
	private LoginRepo loginRepo;

//	public LoginModel saveUser(LoginModel user) {
//		return userRepository.save(user);
//	}
//
//	public LoginModel findByUsername(String username) {
//		return userRepository.findByUsername(username);
//	}
	
	  public LoginModel login(String username, String password) {
	        LoginModel user = loginRepo.findByUsername(username);
	        if (user != null && user.getPassword().equals(password)) {
	            return user;
	        }
	        return null;
	    }
}

