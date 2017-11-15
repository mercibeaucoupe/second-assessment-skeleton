package com.cooksys.twitter.tweets;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.cooksys.twitter.entity.Users;

@Entity
@DiscriminatorValue("Reply")
public class Reply extends Tweet {
	
	@OneToOne
	private Tweet repostOf;
	
	private String content;

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

}
