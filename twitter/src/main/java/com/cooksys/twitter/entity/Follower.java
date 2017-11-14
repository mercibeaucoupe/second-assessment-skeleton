package com.cooksys.twitter.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.cooksys.twitter.tweets.Tweet;

@Entity
public class Follower {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String username;
	
	@OneToOne
	private Profile profile;
	
	@OneToOne
	private Credentials credentials;
	
	@OneToMany
	private List<Tweet> tweets;
	
	private Long timestamp;
	
	private boolean active;
	
	@ManyToOne
	private User user;
	
	public Follower(User user) {
		this.user = user;
		setId(user.getId());
		setUsername(user.getUsername());
		setProfile(user.getProfile());
		setCredentials(user.getCredentials());
		setTimestamp(user.getTimestamp());
		setActive(user.isActive());
	}
	
	public Follower() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
