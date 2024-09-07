package com.newautomaticpapergenerationwebsite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newautomaticpapergenerationwebsite.model.LoginModel;
import com.newautomaticpapergenerationwebsite.service.LoginService;

@CrossOrigin
@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

// only for saving user details 
//	@PostMapping("/login")
//	public LoginModel login(@RequestBody LoginModel user) {
//		
//		return userService.saveUser(user);
//	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> loginData) {
		String userId = loginData.get("userId");
		String password = loginData.get("password");
		LoginModel user = loginService.login(userId, password);
		if (user != null) {
			// Ensure the userType is properly retrieved and sent back
			String userType = user.getUserType();
			if ("A".equals(userType) || "I".equals(userType)) {
				return ResponseEntity.ok(userType); // Return userType directly
			} else {
				return ResponseEntity.status(400).body("Unknown user type in database.");
			}
		} else {
			return ResponseEntity.status(401).body("Invalid credentials");
		}
	}

}
