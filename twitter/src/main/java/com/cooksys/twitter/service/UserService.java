package com.cooksys.twitter.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter.dto.CredentialsDto;
import com.cooksys.twitter.dto.ProfileDto;
import com.cooksys.twitter.dto.UserDto;
import com.cooksys.twitter.entity.Credentials;
import com.cooksys.twitter.entity.Follower;
import com.cooksys.twitter.entity.Following;
import com.cooksys.twitter.entity.Profile;
import com.cooksys.twitter.entity.User;
import com.cooksys.twitter.mapper.CredentialsMapper;
import com.cooksys.twitter.mapper.ProfileMapper;
import com.cooksys.twitter.mapper.UserMapper;
import com.cooksys.twitter.repository.CredentialRepository;
import com.cooksys.twitter.repository.UserRepository;
import com.cooksys.twitter.tweets.Tweet;

@Service
public class UserService {
	
	UserRepository userRepository;
	CredentialRepository credentialRepository;
	UserMapper userMapper;
	CredentialsMapper credentialsMapper;
	ProfileMapper profileMapper;

	private Timestamp timestamp;
	
	public UserService(UserRepository userRepository, CredentialRepository credentialRepository, UserMapper userMapper, CredentialsMapper credentialsMapper, ProfileMapper profileMapper) {
		this.userRepository = userRepository;
		this.credentialRepository = credentialRepository;
		this.userMapper = userMapper;
		this.credentialsMapper = credentialsMapper;
		this.profileMapper = profileMapper;
	}

	public boolean usernameExists(String username) {
		return userRepository.findByUsername(username) != null;
	}

	public List<UserDto> getAll() {
		return userRepository.findByActiveTrue().stream().map( userMapper::toDto ).collect(Collectors.toList());
	}

	@Transactional
	public UserDto createUser(CredentialsDto credentials, ProfileDto profile) {
		Profile newProfile = profileMapper.fromDto(profile);
		Credentials newCredentials = credentialsMapper.fromDto(credentials);

		if (credentialRepository.findByUsername(credentials.getUsername()) != null) {
			User unactiveUser = userRepository.findByCredentials_Username(credentials.getUsername());
		    unactiveUser.setActive(true);
			return userMapper.toDto(unactiveUser);
		}
		
		User newUser = new User(credentials.getUsername(), newProfile, timestamp);
		newUser.setCredentials(newCredentials);
		newCredentials.setUser(newUser);
		newUser.setProfile(newProfile);
		newProfile.setUser(newUser);
		return userMapper.toDto(userRepository.save(newUser));
	}

	public UserDto getByUsername(String username) {
		return userMapper.toDto(userRepository.findByUsername(username));
	}
	
	@Transactional
	public UserDto update(String username, Credentials credentials, Profile profile) {
		User updateUser = userRepository.findByUsername(username);
		if (updateUser == null)
			System.out.println("User is not registered");
		if (updateUser.isActive()) {
			if (updateUser.getCredentials().equals(credentials)) {
			    updateUser.setProfile(profile);
			    profile.setUser(updateUser);
			} else {
				System.out.println("Credentials were wrong");
			}
		}
		return userMapper.toDto(updateUser);
	}
    
	@Transactional
	public UserDto delete(String username, Credentials credentials) {
		User deleteUser = userRepository.findByUsername(username);
		if (deleteUser == null || !deleteUser.isActive()) {
			System.out.println("User is not registered");
		}
		if (deleteUser.getCredentials().equals(credentials)) {
			deleteUser.setActive(false);
		} else {
			System.out.println("Credentials were wrong");
		}
		
		return userMapper.toDto(deleteUser);
	}
	
    @Transactional
	public void follow(String username, Credentials credentials) {
		User followRecipient = userRepository.findByUsername(username);
		if (followRecipient == null || !followRecipient.isActive()) {
			System.out.println("User is not registered");
		}
		User follower = userRepository.findByCredentials_Username(credentials.getUsername());
		if (follower == null) {
			System.out.println("Credentials were wrong");
		}
		if (followRecipient.getFollowers().contains(follower) || follower.getFollowing().contains(followRecipient)) {
			System.out.println("Already following user");
		}
		follower.getFollowing().add(new Following(followRecipient));
		followRecipient.getFollowers().add(new Follower(follower));
	}

	public void unfollow(String username, Credentials credentials) {
		// TODO Auto-generated method stub
		
	}

	public List<Tweet> getTweets(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Tweet> getMentions(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserDto> getFollowing(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserDto> getFollowers(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
