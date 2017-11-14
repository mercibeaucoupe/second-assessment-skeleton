package com.cooksys.twitter.tweets;

import java.sql.Timestamp;

import com.cooksys.twitter.entity.User;

public class Repost extends Tweet {
	
	private Tweet repostOf;

	public Repost(User author, Timestamp posted, Tweet repostOf) {
		super(author, posted);
		this.repostOf = repostOf;
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}

}
