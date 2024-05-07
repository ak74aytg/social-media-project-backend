package com.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.dao.ReelDao;
import com.social.model.Reels;
import com.social.model.User;

@Service
public class ReelServiceImpl implements ReelService {
	@Autowired
	private ReelDao reelDao;
	@Autowired
	private UserService userService;

	@Override
	public Reels createReel(Reels reel, User user) {
		reel.setUser(user);
		return reelDao.save(reel);
	}

	@Override
	public List<Reels> findAllReels() {
		return reelDao.findAll();
	}

	@Override
	public List<Reels> findUsersReel(Integer userId) throws Exception {
		User user = userService.findUserById(userId);
		return reelDao.findByUserId(user.getId());
	}

}
