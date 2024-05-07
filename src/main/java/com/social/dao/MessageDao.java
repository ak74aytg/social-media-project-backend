package com.social.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.model.Message;

@Repository
public interface MessageDao extends JpaRepository<Message, Integer> {
	public List<Message> findByChatId(Integer chatId);

}
