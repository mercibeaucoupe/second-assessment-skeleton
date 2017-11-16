package com.cooksys.twitter.tweets;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.cooksys.twitter.entity.Users;

@Entity
public class SimpleTweet extends Tweet {
	
	private String content;

	@ManyToMany
	private List<Hashtag> hashtags;
	@ManyToMany
	private List<Users> mentioned;
	
	public SimpleTweet(Users author, String content, long posted) {
		super(author, posted, "Simple Tweet");
		this.content = content;
 	}
	
	public SimpleTweet() {
		super();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public List<Hashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<Hashtag> hashtags) {
		this.hashtags = hashtags;
		super.setHashTag(this.hashtags);
	}

	public List<Users> getMentioned() {
		return mentioned;
	}

	public void setMentioned(List<Users> mentioned) {
		this.mentioned = mentioned;
		super.setMentioned(this.mentioned);
	}
	
}
