package com.cooksys.twitter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter.service.HashtagService;
import com.cooksys.twitter.service.UserService;

@RestController
@RequestMapping("validate")
public class ValidateController {
	
	UserService userService;
	HashtagService hashTagService;
	
	public ValidateController(HashtagService hashTagService, UserService userService) {
		this.hashTagService = hashTagService;
		this.userService = userService;
	}
	
	@GetMapping("/tag/exists/{label}")
	public boolean hasLabel(@PathVariable String label) {
		return hashTagService.getAllByLabel(label);
	}
	
	@GetMapping("/username/exists/@{username}")
	public boolean hasUsername(@PathVariable String username) {
		return userService.usernameExists(username);
	}
	
	@GetMapping("/username/available/@{username}")
	public boolean isAvailable(@PathVariable String username) {
		return userService.usernameExists(username);
	}

}
