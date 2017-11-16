package com.cooksys.twitter.tweets;

import java.util.List;

import javax.persistence.CascadeType;
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
	
	private long firstUsed;
	
	private long lastUsed;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Tweet> tweets;
	
	public Hashtag(String label, long firstUsed, long lastUsed) {
		this.label = label;
		this.firstUsed = firstUsed;
		this.lastUsed = lastUsed;
	}
	
	public Hashtag() {
		
	}

	public List<Tweet> getTweets() {
		return tweets;
	}
	
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public long getFirstUsed() {
		return firstUsed;
	}

	public long getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(long lastUsed) {
		this.lastUsed = lastUsed;
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
