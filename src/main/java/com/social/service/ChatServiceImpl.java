package com.social.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.dao.ChatDao;
import com.social.model.Chat;
import com.social.model.User;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	ChatDao chatDao;

	@Override
	public Chat createChat(User currUser, User user) {
		Chat isExist = chatDao.findChatByUsersId(user, currUser);
		if(isExist!=null) {
			return isExist;
		}
		Chat chat = new Chat();
		chat.getUsers().add(user);
		chat.getUsers().add(currUser);
		chat.setTimestamp(LocalDateTime.now());
		return chatDao.save(chat);
	}

	@Override
	public Chat findChatById(Integer chatId) throws Exception {
		Optional<Chat> opt = chatDao.findById(chatId);
		if(opt.isEmpty()) {
			throw new Exception("no chat found with id "+chatId);
		}
		return opt.get();
	}

	@Override
	public List<Chat> findUsersChats(Integer userId) {
		return chatDao.findByUsersId(userId);
	}

}
