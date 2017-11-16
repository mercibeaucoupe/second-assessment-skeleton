package com.cooksys.twitter.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.cooksys.twitter.tweets.Tweet;

@Entity
public class Users {
	
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
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Tweet> liked;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Tweet> mentions;
	

	@OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<Follower> followers;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<Following> following;
	
	private long timestamp;
	
	private boolean active;

	public Users(String username, Profile profile, long timestamp) {
		this.username = username;
		this.profile = profile;
		this.timestamp = timestamp;
		setActive(true);
	}
	
	public Users() {
		
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

	public long getTimestamp() {
		return timestamp;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Tweet> getLiked() {
		return liked;
	}

	public void setLiked(List<Tweet> liked) {
		this.liked = liked;
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
	
	public List<Tweet> getMentions() {
		return mentions;
	}

	public void setMentions(List<Tweet> mentions) {
		this.mentions = mentions;
	}
	
	public List<Follower> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Follower> followers) {
		this.followers = followers;
	}

	public List<Following> getFollowing() {
		return following;
	}

	public void setFollowing(List<Following> following) {
		this.following = following;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Users))
			return false;
		Users other = (Users) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		
	}
}
