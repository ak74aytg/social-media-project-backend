package com.social.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.dao.CommentDao;
import com.social.dao.PostDao;
import com.social.model.Comment;
import com.social.model.Post;
import com.social.model.User;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	@Autowired
	CommentDao commentDao;
	@Autowired
	PostDao postDao;

	@Override
	public Comment createComment(Comment comment, Integer userId, Integer postId) throws Exception {
		User user = userService.findUserById(userId);
		Post post = postService.findPostById(postId);
		comment.setUser(user);
		comment.setCreatedAt(LocalDateTime.now());
		Comment savedComment = commentDao.save(comment);
		post.getComments().add(comment);
		postDao.save(post);
		return savedComment;
	}

	@Override
	public Comment findCommentById(Integer commentId) throws Exception {
		Optional<Comment> opt = commentDao.findById(commentId);
		if(opt.isEmpty()) {
			throw new Exception("no comment found with id "+commentId);
		}
		return opt.get();
	}

	@Override
	public Comment likeComment(Integer commentId, Integer userId) throws Exception {
		User user = userService.findUserById(userId);
		Comment comment = this.findCommentById(commentId);
		if(comment.getLiked().contains(user)) {
			comment.getLiked().remove(user);
		}else {
			comment.getLiked().add(user);
		}
		return commentDao.save(comment);
	}

}
