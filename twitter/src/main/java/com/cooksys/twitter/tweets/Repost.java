package com.cooksys.twitter.tweets;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.cooksys.twitter.entity.Users;

@Entity
public class Repost extends Tweet {
	
	@OneToOne
	private Tweet repostOf;
	
	public Repost(Users author, long posted, Tweet repostOf) {
		super(author, posted, "Repost");
		this.repostOf = repostOf;
	}
	
	public Repost() {
		super();
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}

}
