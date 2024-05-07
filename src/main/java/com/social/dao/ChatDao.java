package com.social.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.social.model.Chat;
import com.social.model.User;

@Repository
public interface ChatDao extends JpaRepository<Chat, Integer> {
	public List<Chat> findByUsersId(Integer userId);

	@Query("SELECT c FROM Chat c WHERE :user MEMBER OF c.users AND :currUser MEMBER OF c.users")
	public Chat findChatByUsersId(@Param("user") User user, @Param("currUser") User currUser);

}
