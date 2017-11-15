package com.cooksys.twitter.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TweetDto {

	private Integer id;
	
	private String type;
		
	private UserDto author;
	
	private List<HashtagDto> hashTag;
	
    private long posted;

    @JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserDto getAuthor() {
		return author;
	}

	public void setAuthor(UserDto author) {
		this.author = author;
	}

	public List<HashtagDto> getHashTag() {
		return hashTag;
	}

	public void setHashTag(List<HashtagDto> hashTag) {
		this.hashTag = hashTag;
	}

	@JsonIgnore
	public long getPosted() {
		return posted;
	}

	public void setPosted(long posted) {
		this.posted = posted;
	}
}
