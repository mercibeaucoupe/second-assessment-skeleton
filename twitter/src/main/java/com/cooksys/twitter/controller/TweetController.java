package com.cooksys.twitter.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter.dto.CredentialsDto;
import com.cooksys.twitter.dto.HashtagDto;
import com.cooksys.twitter.dto.TweetDto;
import com.cooksys.twitter.dto.UserDto;
import com.cooksys.twitter.dto.ReplyDto;
import com.cooksys.twitter.dto.RepostDto;
import com.cooksys.twitter.dto.SimpleTweetDto;
import com.cooksys.twitter.exception.CredentialsNoMatchException;
import com.cooksys.twitter.exception.TweetDoesNotExistException;
import com.cooksys.twitter.exception.UserDoesNotExistException;
import com.cooksys.twitter.helper.ContentCredentials;
import com.cooksys.twitter.service.TweetService;

@RestController
@RequestMapping("tweets")
public class TweetController {

	
	private TweetService tweetService;

	public TweetController(TweetService tweetService) {
		this.tweetService = tweetService;
	}
	
	@GetMapping
	public List<TweetDto> getAllTweets() {
		return tweetService.getAll();
	}
	
	@PostMapping
	public SimpleTweetDto postTweet(@RequestBody ContentCredentials contentCredentials, HttpServletResponse response) 
			throws IOException, UserDoesNotExistException, CredentialsNoMatchException {
		
		return tweetService.post(contentCredentials, response);
	}
	
	@GetMapping("/{id}")
	public TweetDto getTweet(@PathVariable Integer id, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException {
		
		return tweetService.get(id, response);
	}
	
	@DeleteMapping("/{id}")
	public TweetDto deleteTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentials, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException, CredentialsNoMatchException {
		
		return tweetService.delete(id, credentials, response);
	}
	
	@PostMapping("/{id}/like")
	public void likeTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentials, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException, CredentialsNoMatchException {
		
		tweetService.like(id, credentials, response);
	}
	
	@PostMapping("/{id}/reply")
	public ReplyDto replyTweet(@PathVariable Integer id, @RequestBody ContentCredentials user, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException, CredentialsNoMatchException {
		
		return tweetService.reply(id, user, response);
	}
	
	@PostMapping("/{id}/repost")
	public RepostDto repost(@PathVariable Integer id, @RequestBody CredentialsDto user, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException {
		return tweetService.repost(id, user, response);
	}
	
	@GetMapping("/{id}/tags")
	public List<HashtagDto> tags(@PathVariable Integer id, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException {
		return tweetService.getTags(id, response);
	}
	
	@GetMapping("/{id}/likes")
	public List<UserDto> likes(@PathVariable Integer id, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException {
		return tweetService.getLikes(id, response);
	}
	
	/* @GetMapping("/{id}/context")
	public Context 
	*/
	
	@GetMapping("/{id}/replies")
	public List<ReplyDto> replies(@PathVariable Integer id, HttpServletResponse response) {
		return tweetService.getReplies(id, response);
	}
	
	@GetMapping("/{id}/mentions")
	public List<UserDto> mentions(@PathVariable Integer id, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException {
		
		return tweetService.getMentions(id, response);
	}
	
}
