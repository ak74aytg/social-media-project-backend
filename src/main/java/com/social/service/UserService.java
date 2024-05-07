package com.social.service;

import java.util.List;

import com.social.model.User;

public interface UserService {
	public List<User> findAll(); 
	public User registerUser(User user);
	public User findUserById(Integer userId) throws Exception;
	public User findUserByEmail(String email) throws Exception;
	public User followUser(Integer userId1, Integer userId2) throws Exception;
	public User updateUser(User user, Integer id) throws Exception;
	public List<User> searchUser(String query);
	public User getUserByToken(String jwt);
}
