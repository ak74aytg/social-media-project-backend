package com.social.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.model.Reels;

@Repository
public interface ReelDao extends JpaRepository<Reels, Integer> {
	public List<Reels> findByUserId(Integer userId);
}
