package com.cooksys.twitter.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter.dto.CredentialsDto;
import com.cooksys.twitter.dto.FollowerDto;
import com.cooksys.twitter.dto.FollowingDto;
import com.cooksys.twitter.dto.TweetDto;
import com.cooksys.twitter.dto.UserDto;
import com.cooksys.twitter.exception.CredentialsNoMatchException;
import com.cooksys.twitter.exception.FollowingException;
import com.cooksys.twitter.exception.UserDoesNotExistException;
import com.cooksys.twitter.helper.CredentialsProfile;
import com.cooksys.twitter.service.UserService;

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
	public UserDto addNewUser(@RequestBody CredentialsProfile credentialsProfile, HttpServletResponse response) 
			throws CredentialsNoMatchException, IOException {

		return userService.createUser(credentialsProfile, response);

	}
	
	@GetMapping("/@{username}")
	public UserDto getByName(@PathVariable String username, HttpServletResponse response) throws IOException, UserDoesNotExistException {
		    return userService.getByUsername(username, response);
	}
	
	@PatchMapping("/@{username}")
	public UserDto updateProfile(@PathVariable String username, 
			@RequestBody CredentialsProfile credentialsProfile, HttpServletResponse response)
			throws IOException, CredentialsNoMatchException, UserDoesNotExistException {
		
			return userService.update(username, credentialsProfile, response);

	}
	
	@DeleteMapping("/@{username}")
	public UserDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentials, HttpServletResponse response) 
			throws IOException, UserDoesNotExistException, CredentialsNoMatchException {
		return userService.delete(username, credentials, response);
	}
	
	@PostMapping("/@{username}/follow")
	public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentials, HttpServletResponse response) 
			throws IOException, UserDoesNotExistException, CredentialsNoMatchException, FollowingException {
		
		userService.follow(username, credentials, response);

	}
	
	@PostMapping("/@{username}/unfollow")
	public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentials, HttpServletResponse response) 
			throws IOException, UserDoesNotExistException, CredentialsNoMatchException, FollowingException {
		
		userService.unfollow(username, credentials, response);
		
	}
	
	@GetMapping("/@{username}/feed")
	public List<TweetDto> getUserTweets(@PathVariable String username, HttpServletResponse response) 
			throws IOException, UserDoesNotExistException {
		
		return userService.getTweets(username, response);
		
	}
	
	@GetMapping("/@{username}/mentions")
	public List<TweetDto> getUserMentions(@PathVariable String username) {
		return userService.getMentions(username);
	}
	
	@GetMapping("/@{username}/followers")
	public List<FollowerDto> getUserFollowers(@PathVariable String username, HttpServletResponse response) 
			throws IOException, UserDoesNotExistException {
		
		return userService.getFollowers(username, response);
		
	}
	
	@GetMapping("@{username}/following")
	public List<FollowingDto> getUserFollowing(@PathVariable String username, HttpServletResponse response)
			throws IOException, UserDoesNotExistException {
		
		return userService.getFollowing(username, response);
		
	}
}