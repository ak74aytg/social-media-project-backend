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

import com.social.model.Message;
import com.social.model.User;
import com.social.service.MessageService;
import com.social.service.UserService;

@Controller
@RequestMapping("/api/messages/chat")
public class MessageController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/{chatId}")
	public ResponseEntity<?> createMessageHandler(@RequestBody Message message, @PathVariable("chatId") Integer chatId, @RequestHeader("Authorization") String jwt) {
		//TODO: process POST request
		try {
			User user = userService.getUserByToken(jwt);
			message = messageService.createMessage(user, chatId, message);
			return ResponseEntity.ok(message);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	
	@GetMapping("/{chatId}")
	public ResponseEntity<?> findChatsMessageHandler(@PathVariable("chatId") Integer chatId) {
		//TODO: process POST request
		try {
			List<Message> messages = messageService.findChatsMessages(chatId);
			return ResponseEntity.ok(messages);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
}
