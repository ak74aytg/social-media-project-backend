package com.social.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.dao.PostDao;
import com.social.dao.UserDao;
import com.social.model.Post;
import com.social.model.User;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	PostDao postDao;
	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;

	@Override
	public Post createNewPost(Post post, Integer userId) throws Exception {
		post.setCreatedAt(LocalDateTime.now());
		User user = userService.findUserById(userId);
		post.setUser(user);
		return postDao.save(post);
	}

	@Override
	public String deletePost(Integer postId, Integer userId) throws Exception {
		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		if(user.getId()==post.getUser().getId()) {
			postDao.delete(post);
			return "post deleted successfully";
		}
		throw new Exception("You are not allowed to delete this post");
	}

	@Override
	public List<Post> findPostByUserId(Integer userId) {
		return postDao.findPostByUserId(userId);
	}

	@Override
	public Post findPostById(Integer postId) throws Exception {
		Optional<Post> post = postDao.findById(postId);
		if(post.isEmpty()) {
			throw new Exception("post not found with id "+postId);
		}
		return post.get();
	}

	@Override
	public List<Post> findAllPost() {
		return postDao.findAll();
	}

	@Override
	public Post savedPost(Integer postId, Integer userId) throws Exception {
		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		if(user.getSavedPosts().contains(post)) {
			user.getSavedPosts().remove(post);
		}else
			user.getSavedPosts().add(post);
		userDao.save(user);
		return post;
	}

	@Override
	public Post likePost(Integer postId, Integer userId) throws Exception {
		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		if(post.getLiked().contains(user)) {
			post.getLiked().remove(user);
		}else
			post.getLiked().add(user);
		return postDao.save(post);
	}

}
