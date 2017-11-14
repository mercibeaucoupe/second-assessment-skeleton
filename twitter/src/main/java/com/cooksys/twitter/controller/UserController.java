package com.cooksys.twitter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter.dto.CredentialsDto;
import com.cooksys.twitter.dto.ProfileDto;
import com.cooksys.twitter.dto.UserDto;
import com.cooksys.twitter.entity.Credentials;
import com.cooksys.twitter.entity.Profile;
import com.cooksys.twitter.service.UserService;
import com.cooksys.twitter.tweets.Tweet;

@RestController
@RequestMapping("users")
public class UserController {
	
	UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public List<UserDto> getUsers() {
		return userService.getAll();
	}
	
	@PostMapping
	public UserDto addNewUser(@RequestBody CredentialsDto credentials, @RequestBody ProfileDto profile) {
		return userService.createUser(credentials, profile);
	}
	
	@GetMapping("/@{username}")
	public UserDto getByName(@PathVariable String username) {
		return userService.getByUsername(username);
	}
	
	@PatchMapping("/@{username}")
	public UserDto updateProfile(@PathVariable String username, @RequestBody Credentials credentials, @RequestBody Profile profile) {
		return userService.update(username, credentials, profile);
	}
	
	@DeleteMapping("/@{username}")
	public UserDto deleteUser(@PathVariable String username, @RequestBody Credentials credentials) {
		return userService.delete(username, credentials);
	}
	
	// Needs to throw Exception
	@PostMapping("/@{username}/follow")
	public void followUser(@PathVariable String username, @RequestBody Credentials credentials) {
		userService.follow(username, credentials);
	}
	
	// Needs to throw Exception
	@PostMapping("/@{username}/unfollow")
	public void unfollowUser(@PathVariable String username, @RequestBody Credentials credentials) {
		userService.unfollow(username, credentials);
	}
	
	// Needs to Throw Exception if user doesnt exist
	@GetMapping("/@{username}/feed")
	public List<Tweet> getUserTweets(@PathVariable String username) {
		return userService.getTweets(username);
	}
	
	@GetMapping("/@{username}/mentions")
	public List<Tweet> getUserMentions(@PathVariable String username) {
		return userService.getMentions(username);
	}
	
	@GetMapping("/@{username}/followers")
	public List<UserDto> getUserFollowers(@PathVariable String username) {
		return userService.getFollowers(username);
	}
	
	@GetMapping("@{username}/following")
	public List<UserDto> getUserFollowing(@PathVariable String username) {
		return userService.getFollowing(username);
	}
}