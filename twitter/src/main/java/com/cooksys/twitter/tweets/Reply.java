package com.cooksys.twitter.tweets;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.cooksys.twitter.entity.Users;

@Entity
public class Reply extends Tweet {
	
	@OneToOne
	private Tweet repostOf;
	
	@OneToOne
	private Tweet repostTo;
	
	private String content;
	
	@ManyToMany
	private List<Hashtag> hashtags;
	
	@ManyToMany
	private List<Users> mentioned;
	

	public Reply(Users author, long posted, String content, Tweet repostOf) {
		super(author, posted, "Reply");
		this.setRepostOf(repostOf);
		this.setContent(content);
	}
	
	public Reply() {
		
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
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
