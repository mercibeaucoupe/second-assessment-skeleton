package com.cooksys.twitter.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FollowingDto {

    private Integer id;
	
	private String username;

	private Long timestamp;
	
	private boolean active;

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@JsonIgnore
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}