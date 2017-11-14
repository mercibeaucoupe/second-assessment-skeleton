package com.cooksys.twitter.tweets;

import java.sql.Timestamp;

import com.cooksys.twitter.entity.User;

public class Reply extends SimpleTweet {
	
	private Tweet repostOf;

	public Reply(User author, Timestamp posted, String content, Tweet repostOf) {
		super(author, posted, content);
		this.setRepostOf(repostOf);
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}

}
