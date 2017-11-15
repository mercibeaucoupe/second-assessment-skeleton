package com.cooksys.twitter.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class HashtagDto {

	private Integer id;
	
	private String label;
	
	private long firstUsed;
	
	private long lastUsed;
	
	private List<TweetDto> tweets;

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@JsonIgnore
	public long getFirstUsed() {
		return firstUsed;
	}

	public void setFirstUsed(long firstUsed) {
		this.firstUsed = firstUsed;
	}

	@JsonIgnore
	public long getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(long lastUsed) {
		this.lastUsed = lastUsed;
	}

	@JsonIgnore
	public List<TweetDto> getTweets() {
		return tweets;
	}

	public void setTweets(List<TweetDto> tweets) {
		this.tweets = tweets;
	}
}
