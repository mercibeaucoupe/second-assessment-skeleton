package com.cooksys.twitter.tweets;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.cooksys.twitter.entity.Users;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Tweet {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Users author;
	
	@ManyToMany
	private List<Hashtag> hashTag;
	
	@ManyToMany
	private List<Users> likes;
	
	@ManyToMany
	private List<Users> mentioned;

	private long posted;
	
	private String type;
	
	private boolean active;
	
	public Tweet(Users author, long posted, String type) {
		this.author = author;
		this.posted = posted;
		this.type = type;
		setActive(true);
	}
	
	public Tweet() {
		
	}
	
	public List<Users> getLikes() {
		return likes;
	}

	public void setLikes(List<Users> likes) {
		this.likes = likes;
	}

	public Integer getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getAuthor() {
		return author;
	}

	public void setAuthor(Users author) {
		this.author = author;
	}

	public long getPosted() {
		return posted;
	}

	public void setPosted(long posted) {
		this.posted = posted;
	}
	
	public List<Users> getMentioned() {
		return mentioned;
	}

	public void setMentioned(List<Users> mentioned) {
		this.mentioned = mentioned;
	}

	public List<Hashtag> getHashTag() {
		return hashTag;
	}

	public void setHashTag(List<Hashtag> hashTag) {
		this.hashTag = hashTag;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
