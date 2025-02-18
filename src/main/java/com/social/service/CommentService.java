package com.social.service;

import com.social.model.Comment;

public interface CommentService {

	public Comment createComment(Comment comment, Integer userId, Integer postId) throws Exception;
	public Comment findCommentById(Integer commentId) throws Exception;
	public Comment likeComment(Integer commentId, Integer userId) throws Exception;
}
