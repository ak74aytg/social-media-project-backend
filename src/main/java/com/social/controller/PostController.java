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
import org.springframework.web.bind.annotation.RestController;

import com.social.dao.UserDao;
import com.social.model.Post;
import com.social.model.User;
import com.social.service.PostService;
import com.social.service.UserService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	PostService postService;
	@Autowired
	UserService userService;

	
	
	@PostMapping("/posts")
	public ResponseEntity<?> createPostHandler(@RequestHeader("Authorization") String jwt, @RequestBody Post post){
		Post newPost;
		try {
			User reqUser = userService.getUserByToken(jwt);
			newPost = postService.createNewPost(post, reqUser.getId());
			return ResponseEntity.ok(newPost);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt){
		try {
			User reqUser = userService.getUserByToken(jwt);
		   String message = postService.deletePost(postId, reqUser.getId());
		   return ResponseEntity.ok(message);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}
	
	@GetMapping("/posts")
	public List<Post> findAllPostHandler(){
		return postService.findAllPost();
	}
	
	@GetMapping("/posts/user/{userId}")
	public List<Post> findPostByUserIdHandler(@PathVariable Integer userId){
		return postService.findPostByUserId(userId);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<?> findPostById(@PathVariable Integer postId){
		try {
			Post post = postService.findPostById(postId);
			return ResponseEntity.ok(post);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PutMapping("/posts/save/{postId}")
	public ResponseEntity<?> savePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt){
		try {
			User reqUser = userService.getUserByToken(jwt);
			Post post = postService.savedPost(postId, reqUser.getId());
			return ResponseEntity.ok(post);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PutMapping("/posts/like/{postId}")
	public ResponseEntity<?> likePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt){
		try {
			User reqUser = userService.getUserByToken(jwt);
			Post post = postService.likePost(postId, reqUser.getId());
			return ResponseEntity.ok(post);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}
