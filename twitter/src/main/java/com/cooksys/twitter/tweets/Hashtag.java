package com.cooksys.twitter.tweets;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Hashtag {
	
	@Id
	@GeneratedValue
	private Integer id;

	private String label;
	
	private Long firstUsed;
	
	private Long lastUsed;
	
	@ManyToMany
	private List<Tweet> tweets;
	
	public Hashtag(String label, Timestamp firstUsed, Timestamp lastUsed) {
		this.label = label;
		this.firstUsed = firstUsed.getTime();
		this.lastUsed = lastUsed.getTime();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getFirstUsed() {
		return firstUsed;
	}

	public Long getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Timestamp lastUsed) {
		this.lastUsed = lastUsed.getTime();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Hashtag))
			return false;
		Hashtag other = (Hashtag) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	
}
