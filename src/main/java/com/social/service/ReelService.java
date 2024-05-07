package com.social.service;

import java.util.List;

import com.social.model.Reels;
import com.social.model.User;

public interface ReelService {
	public Reels createReel(Reels reel, User user);
	public List<Reels> findAllReels();
	public List<Reels> findUsersReel(Integer userId) throws Exception; 
}
