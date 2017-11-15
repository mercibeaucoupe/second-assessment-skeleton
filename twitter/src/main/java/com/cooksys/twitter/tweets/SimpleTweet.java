package com.cooksys.twitter.tweets;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.cooksys.twitter.entity.Users;

@Entity
@DiscriminatorValue("SimpleTweet")
public class SimpleTweet extends Tweet {
	
	private String content;
	
	public SimpleTweet(Users author, String content, long posted) {
		super(author, posted, "Simple Tweet");
		this.content = content;
 	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
