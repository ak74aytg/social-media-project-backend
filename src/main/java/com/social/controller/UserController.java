package com.social.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.dao.UserDao;
import com.social.model.User;
import com.social.service.UserService;

@RestController
@RequestMapping("/api/")
public class UserController {
	@Autowired
	UserService userService;
	
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.findAll();
	}
	
	@GetMapping("users/{userId}")
	public ResponseEntity<?> getUserByIdHandler(@PathVariable("userId") Integer id) {
		User user;
		try {
			user = userService.findUserById(id);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	
	
	@PutMapping("users")
	public ResponseEntity<?> updateUserHandler(@RequestHeader("Authorization") String jwt ,@RequestBody User user) {
		User updatedUser;
		try {
			User reqUser = userService.getUserByToken(jwt);
			updatedUser = userService.updateUser(user, reqUser.getId());
			return ResponseEntity.ok(updatedUser);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	
	@PutMapping("users/{userId}")
	public ResponseEntity<?> followUserHandler(@RequestHeader("Authorization") String jwt, @PathVariable("userId") Integer userId){
		try {
			User reqUser = userService.getUserByToken(jwt);
			User user = userService.followUser(reqUser.getId(), userId);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	
	@GetMapping("users/search")
	public List<User> searchUser(@RequestParam("query") String query){
		List<User> users = userService.searchUser(query);
		return users;
	}
	
	
	@GetMapping("users/profile")
	public User getUserProfile(@RequestHeader("Authorization") String jwt) {
		User user = userService.getUserByToken(jwt);
		return user;
	}
}
