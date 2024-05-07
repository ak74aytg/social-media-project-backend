package com.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.social.model.Comment;
import com.social.model.User;
import com.social.service.CommentService;
import com.social.service.UserService;

@Controller
@RequestMapping("/api/comments/")
public class CommentController {
	@Autowired
	CommentService commentService;
	@Autowired
	UserService userService;
	
	@PostMapping("post/{postId}")
	public ResponseEntity<?> createCommentHandler(@RequestBody Comment comment, @PathVariable Integer postId, @RequestHeader("Authorization") String jwt) {
		try {
			User user = userService.getUserByToken(jwt);
			Comment savedComment = commentService.createComment(comment, user.getId(), postId);
			return ResponseEntity.ok(savedComment);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}	
	}
	
	
	@PutMapping("{commentId}")
	public ResponseEntity<?> likeCommentHandler(@PathVariable Integer commentId, @RequestHeader("Authorization") String jwt) {
		try {
			User user = userService.getUserByToken(jwt);
			Comment savedComment = commentService.likeComment(commentId, user.getId());
			return ResponseEntity.ok(savedComment);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}	
	}
}
