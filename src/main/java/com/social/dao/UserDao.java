package com.social.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.social.model.Post;
import com.social.model.User;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.firstName LIKE %:query% OR u.lastName LIKE %:query% OR u.email LIKE %:query%")
	public List<User> searchUser(@Param("query") String query);
	
	@Query("SELECT u.savedPosts FROM User u WHERE u.id = :userId")
    List<Post> findSavedPostsByUserId(Integer userId);
}
