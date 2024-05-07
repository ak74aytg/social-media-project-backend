package com.social.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.dao.ChatDao;
import com.social.dao.MessageDao;
import com.social.model.Chat;
import com.social.model.Message;
import com.social.model.User;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	ChatService chatService;
	@Autowired
	MessageDao messageDao;
	@Autowired
	ChatDao chatDao;

	@Override
	public Message createMessage(User user, Integer chatId, Message message) throws Exception {
		Chat chat = chatService.findChatById(chatId);
		message.setChat(chat);
		message.setUser(user);
		message.setTimestamp(LocalDateTime.now());
		Message savedMessage = messageDao.save(message);
		chat.getMessages().add(savedMessage);
		chatDao.save(chat);
		return savedMessage;
	}

	@Override
	public List<Message> findChatsMessages(Integer chatId) throws Exception {
		Chat chat = chatService.findChatById(chatId);
		return messageDao.findByChatId(chat.getId());
	}

}
