package com.social.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.social.model.Post;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {

	@Query("SELECT p FROM Post p WHERE p.user.id=:userId")
	List<Post> findPostByUserId(Integer userId);
}
