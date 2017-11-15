package com.cooksys.twitter.helper;

import com.cooksys.twitter.dto.CredentialsDto;
import com.cooksys.twitter.dto.ProfileDto;

public class CredentialsProfile {

	private CredentialsDto credentials;
	
	private ProfileDto profile;

	public CredentialsDto getCredentials() {
		return credentials;
	}

	public void setCredentials(CredentialsDto credentials) {
		this.credentials = credentials;
	}

	public ProfileDto getProfile() {
		return profile;
	}

	public void setProfile(ProfileDto profile) {
		this.profile = profile;
	}
}
