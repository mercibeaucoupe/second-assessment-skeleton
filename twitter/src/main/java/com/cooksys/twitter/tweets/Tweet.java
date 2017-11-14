package com.cooksys.twitter.tweets;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.cooksys.twitter.entity.User;

@Entity
public class Tweet {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private User author;
	
	@ManyToMany
	private List<Hashtag> hashTag;

	private Timestamp posted;
	
	public Tweet(User author, Timestamp posted) {
		this.author = author;
		this.posted = posted;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}
	
	public List<Hashtag> getHashTag() {
		return hashTag;
	}

	public void setHashTag(List<Hashtag> hashTag) {
		this.hashTag = hashTag;
	}
}
