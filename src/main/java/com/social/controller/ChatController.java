package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.social.dao.ChatDao;
import com.social.model.Chat;
import com.social.model.User;
import com.social.request.CreateChatRequest;
import com.social.service.ChatService;
import com.social.service.UserService;

@Controller
@RequestMapping("/api")
public class ChatController {
	@Autowired
	UserService userService;
	@Autowired
	ChatService chatService;
	
	@PostMapping("/chats")
	public ResponseEntity<?> createChatHandler(@RequestBody CreateChatRequest user, @RequestHeader("Authorization") String jwt) {
		try {
			User currUser = userService.getUserByToken(jwt);
			User reqUser = userService.findUserById(user.getUserId());
			Chat chat = chatService.createChat(reqUser, currUser);
			return ResponseEntity.ok(chat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/chats/{chatId}")
	public ResponseEntity<?> findChatByIdHandler(@PathVariable("chatId") Integer chatId){
		try {
			Chat chat = chatService.findChatById(chatId);
			return ResponseEntity.ok(chat);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping("/chats")
	public ResponseEntity<?> findUserChatsHandler(@RequestHeader("Authorization") String jwt){
		try {
			User currUser = userService.getUserByToken(jwt);
			List<Chat> chats = chatService.findUsersChats(currUser.getId());
			return ResponseEntity.ok(chats);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
