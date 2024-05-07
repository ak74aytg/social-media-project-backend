package com.social.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.social.config.JwtProvider;
import com.social.dao.UserDao;
import com.social.model.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User registerUser(User user) {
		User savedUser = userDao.save(user);
		return savedUser;
	}

	@Override
	public User findUserById(Integer userId) throws Exception {
		Optional<User> user = userDao.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		throw new Exception("user does not exist with user id "+userId);
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user = userDao.findByEmail(email);
		if(user!=null) {
			return user;
		}
		throw new Exception("user does not exist with user email "+email);
	}

	@Override
	public User followUser(Integer userId1, Integer userId2) throws Exception {
		User user1 = findUserById(userId1);
		User user2 = findUserById(userId2);
		
		if(user1.getFollowings().contains(user2.getId())) {
			user1.getFollowings().remove(user2.getId());
		}else {
			user1.getFollowings().add(user2.getId());			
		}
		if(user2.getFollowers().contains(user1.getId())) {
			user2.getFollowers().remove(user1.getId());
		}else {
			user2.getFollowers().add(user1.getId());
		}
		userDao.save(user1);
		userDao.save(user2);
		return user1;
	}

	@Override
	public User updateUser(User user, Integer id) throws Exception {
		Optional<User> oldUser = userDao.findById(id);
		if(oldUser.isEmpty()) throw new Exception("user does not exist with user id "+id);
		User savedUser = oldUser.get();
		if(user.getFirstName()!=null) {
			savedUser.setFirstName(user.getFirstName());
		}
		if(user.getLastName()!=null) {
			savedUser.setLastName(user.getLastName());
		}
		if(user.getEmail()!=null) {
			savedUser.setEmail(user.getEmail());
		}
		if(user.getGender()!=null) {
			savedUser.setGender(user.getGender());
		}
		if(user.getPassword()!=null) {
			savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		User newUser = userDao.save(savedUser);
		return newUser;
	}

	@Override
	public List<User> searchUser(String query) {
		return userDao.searchUser(query);
	}

	@Override
	public User getUserByToken(String jwt) {
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		User user = userDao.findByEmail(email);
		return user;
	}

}
