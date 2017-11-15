package com.cooksys.twitter.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto {
	
	private Integer id;
	
	private String username;

	private CredentialsDto credentials;
	
	private ProfileDto profile;

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public CredentialsDto getCredentials() {
		return credentials;
	}

	public void setCredentials(CredentialsDto credentials) {
		this.credentials = credentials;
	}

	@JsonIgnore
	public ProfileDto getProfile() {
		return profile;
	}

	public void setProfile(ProfileDto profile) {
		this.profile = profile;
	}

	public String getUsername() {
		return username;
	}
}
