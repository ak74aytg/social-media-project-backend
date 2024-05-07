package com.social.service;

import java.util.List;

import com.social.model.Chat;
import com.social.model.Message;
import com.social.model.User;

public interface MessageService {

	public Message createMessage(User user, Integer chatId, Message message) throws Exception;
	public List<Message> findChatsMessages(Integer chatId) throws Exception;
}
