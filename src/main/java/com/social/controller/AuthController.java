package com.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.config.JwtProvider;
import com.social.dao.UserDao;
import com.social.model.User;
import com.social.request.LoginRequest;
import com.social.response.AuthResponse;
import com.social.service.CustomUserDetailsService;
import com.social.service.UserService;

@RestController
@RequestMapping("/auth/")
public class AuthController {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signup")
	public AuthResponse createUserHandler(@RequestBody User user) throws Exception {
		User isExist = userDao.findByEmail(user.getEmail());
		if(isExist!=null) {
			throw new Exception("email already used with another account");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser = userDao.save(user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		String token = JwtProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(token, "Registered successfully");
		return res;
	}
	
	
	@PostMapping("signin")
	public AuthResponse loginUserHandler(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		String token = JwtProvider.generateToken(authentication);
		AuthResponse res = new AuthResponse(token, "login successfull");
		return res;
	}


	private Authentication authenticate(String email, String password) {
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
		if(userDetails==null) {
			throw new BadCredentialsException("invalid username");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("wrong password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
	}
}
