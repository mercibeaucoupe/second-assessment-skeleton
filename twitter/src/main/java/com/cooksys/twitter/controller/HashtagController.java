package com.cooksys.twitter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter.dto.HashtagDto;
import com.cooksys.twitter.dto.TweetDto;
import com.cooksys.twitter.service.HashtagService;

@RestController
@RequestMapping("tags")
public class HashtagController {

	HashtagService hashtagService;
	
	public HashtagController(HashtagService hashtagService) {
		this.hashtagService = hashtagService;
	}
	
	@GetMapping
	public List<HashtagDto> getAllTags() {
		return hashtagService.getAll();
	}
	
	@GetMapping("/{label}")
	public List<TweetDto> getAllTweetByTag(@PathVariable String label) {
		return hashtagService.getAllByLabel(label);
	}
}
