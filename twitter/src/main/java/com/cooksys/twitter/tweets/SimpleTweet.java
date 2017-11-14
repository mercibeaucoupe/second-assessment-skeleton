package com.cooksys.twitter.tweets;

import java.sql.Timestamp;

import com.cooksys.twitter.entity.User;

public class SimpleTweet extends Tweet {
	
	private String content;

	public SimpleTweet(User author, Timestamp posted, String content) {
		super(author, posted);
		this.content = content;
 	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
