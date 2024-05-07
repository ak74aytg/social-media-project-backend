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

import com.social.model.Reels;
import com.social.model.User;
import com.social.service.ReelService;
import com.social.service.UserService;

@Controller
@RequestMapping("/api")
public class ReelsController {
	@Autowired
	UserService userService;
	@Autowired
	ReelService reelService;

	@PostMapping("/reels")
	public ResponseEntity<?> createReelsHandler(@RequestBody Reels reel, @RequestHeader("Authorization") String jwt) {
		try {
			User user = userService.getUserByToken(jwt);
			reel = reelService.createReel(reel, user);
			return ResponseEntity.ok(reel);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/reels")
	public List<Reels> getAllReelsHandler(){
		return reelService.findAllReels();
	}
	
	@GetMapping("/reels/user/{userId}")
	public ResponseEntity<?> getReelsByUserHandler(@PathVariable("userId") Integer userId){ 
		try {
			List<Reels> list = reelService.findUsersReel(userId);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
