package com.cooksys.twitter.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.login.CredentialException;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter.dto.CredentialsDto;
import com.cooksys.twitter.dto.FollowerDto;
import com.cooksys.twitter.dto.FollowingDto;
import com.cooksys.twitter.dto.ProfileDto;
import com.cooksys.twitter.dto.TweetDto;
import com.cooksys.twitter.dto.UserDto;
import com.cooksys.twitter.entity.Credentials;
import com.cooksys.twitter.entity.Follower;
import com.cooksys.twitter.entity.Following;
import com.cooksys.twitter.entity.Profile;
import com.cooksys.twitter.entity.Users;
import com.cooksys.twitter.exception.CredentialsNoMatchException;
import com.cooksys.twitter.exception.FollowingException;
import com.cooksys.twitter.exception.UnfollowingException;
import com.cooksys.twitter.exception.UserAlreadyExistsException;
import com.cooksys.twitter.exception.UserDoesNotExistException;
import com.cooksys.twitter.helper.CredentialsProfile;
import com.cooksys.twitter.mapper.CredentialsMapper;
import com.cooksys.twitter.mapper.FollowerMapper;
import com.cooksys.twitter.mapper.FollowingMapper;
import com.cooksys.twitter.mapper.ProfileMapper;
import com.cooksys.twitter.mapper.TweetMapper;
import com.cooksys.twitter.mapper.UserMapper;
import com.cooksys.twitter.repository.CredentialRepository;
import com.cooksys.twitter.repository.FollowerRepository;
import com.cooksys.twitter.repository.FollowingRepository;
import com.cooksys.twitter.repository.ProfileRepository;
import com.cooksys.twitter.repository.UserRepository;
import com.cooksys.twitter.tweets.Tweet;

@Service
public class UserService {
	
	UserRepository userRepository;
	CredentialRepository credentialRepository;
	ProfileRepository profileRepository;
	FollowerRepository followerRepository;
	FollowingRepository followingRepository;
	//TweetRepository tweetRepository;
	UserMapper userMapper;
	CredentialsMapper credentialsMapper;
	ProfileMapper profileMapper;
	FollowerMapper followerMapper;
	FollowingMapper followingMapper;
	TweetMapper tweetMapper;

	private Timestamp timestamp;
	
	public UserService(UserRepository userRepository, 
			CredentialRepository credentialRepository, 
			ProfileRepository profileRepository,
			FollowerRepository followerRepository,
			FollowingRepository followingRepository,
			//TweetRepository tweetRepository,
			UserMapper userMapper, 
			CredentialsMapper credentialsMapper, 
			ProfileMapper profileMapper,
			FollowerMapper followerMapper,
			FollowingMapper followingMapper,
			TweetMapper tweetMapper) {
		
		this.userRepository = userRepository;
		this.credentialRepository = credentialRepository;
		this.profileRepository = profileRepository;
		this.followerRepository = followerRepository;
		this.followingRepository = followingRepository;
		//this.tweetRepository = tweetRepository;
		
		this.userMapper = userMapper;
		this.credentialsMapper = credentialsMapper;
		this.profileMapper = profileMapper;
		this.followerMapper = followerMapper;
		this.followingMapper = followingMapper;
		this.tweetMapper = tweetMapper;
	}

	public boolean usernameExists(String username) {
		return userRepository.findByUsernameIgnoreCase(username) != null;
	}

	public List<UserDto> getAll() {
		return userRepository.findByActiveTrue().stream().map( userMapper::toDto ).collect(Collectors.toList());
	}

	@Transactional
	public UserDto createUser(CredentialsProfile user, HttpServletResponse response) 
			throws CredentialsNoMatchException, IOException {

		if (!usernameExists(user.getCredentials().getUsername())) {

			Credentials newCredentials = credentialsMapper.fromDto(user.getCredentials());
            Profile newProfile = profileMapper.fromDto(user.getProfile());
            
            Users newUser = new Users(user.getCredentials().getUsername(), 
            		newProfile, new Timestamp(System.currentTimeMillis()).getTime());
		
            newUser.setCredentials(newCredentials);
	    	newProfile.setUser(newUser);
		    newCredentials.setUser(newUser);
		
		    credentialRepository.save(newCredentials);
		    profileRepository.save(newProfile);
		    userRepository.save(newUser);

		    return userMapper.toDto(newUser);

		} else if (credentialRepository.findByUsername(
				user.getCredentials().getUsername()) != null &&
				!userRepository.findByUsername(
						user.getCredentials().getUsername()).isActive()) {

			Credentials reactiveCredentials = credentialRepository.findByUsername(
					user.getCredentials().getUsername());

			if (credentialsMapper.toDto(reactiveCredentials).equals(user.getCredentials())) {
				Users oldUser = userRepository.findByCredentials_Username(
						user.getCredentials().getUsername());

			    oldUser.setActive(true);
			    return userMapper.toDto(oldUser);
			} else {
			    response.sendError(CredentialsNoMatchException.STATUS_CODE, "Credentials did not match.");
			    return null;
			}
		} else {
			response.sendError(UserAlreadyExistsException.STATUS_CODE, "User with the username "
					+ user.getCredentials().getUsername() + " already exists");
			return null;
		}
	}

	public UserDto getByUsername(String username, HttpServletResponse response) throws UserDoesNotExistException, IOException {
		
		Users searchUser = userRepository.findByUsernameIgnoreCase(username);
		if (searchUser == null || !searchUser.isActive()) {
			response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username " + username);
			return null;
		}
		return userMapper.toDto(searchUser);
	}

	@Transactional
	public UserDto update(String username, CredentialsProfile user, HttpServletResponse response) 
		throws IOException, CredentialsNoMatchException, UserDoesNotExistException {

		Users updateUser = userRepository.findByUsernameIgnoreCase(username);

		UserDto updateUserDto = userMapper.toDto(updateUser);
		
		if (updateUser == null || !updateUser.isActive()) {
			response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username "
		        + username);
		    return null;
		}
		
		if (updateUserDto.getCredentials().equals(user.getCredentials())) {
		    Profile updateProfile = profileMapper.fromDto(user.getProfile());
			updateUser.setProfile(updateProfile);
			updateProfile.setUser(updateUser);
			updateProfile.setId(user.getProfile().getId());
			profileRepository.save(updateProfile);
		} else {
		    response.sendError(CredentialsNoMatchException.STATUS_CODE, "Credentials did not match");
		    return null;
		}
		return userMapper.toDto(updateUser);
	}
    
	@Transactional
	public UserDto delete(String username, CredentialsDto credentials, HttpServletResponse response) 
	    throws IOException, UserDoesNotExistException, CredentialsNoMatchException {

		Users deleteUser = userRepository.findByUsernameIgnoreCase(username);
		
		UserDto deleteUserDto = userMapper.toDto(deleteUser);
		
		if (deleteUser == null || !deleteUser.isActive()) {
			response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username " + username);
			return null;
		}
		System.out.println("Repository username: " + deleteUserDto.getCredentials().getUsername());
		System.out.println("Repository password: " + deleteUserDto.getCredentials().getPassword());;
		System.out.println("User input username: " + credentials.getUsername());
		System.out.println("User input password: " + credentials.getPassword());
		if (deleteUserDto.getCredentials().equals(credentials)) {
			deleteUser.setActive(false);
		} else {
			response.sendError(CredentialsNoMatchException.STATUS_CODE, "Credentials did not match");
			return null;
		}
		return userMapper.toDto(deleteUser);
	}
	
    @Transactional
	public void follow(String username, CredentialsDto credentials, HttpServletResponse response)
			throws IOException, UserDoesNotExistException, CredentialsNoMatchException, FollowingException {
    	
		Users followRecipient = userRepository.findByUsernameIgnoreCase(username);
		
		if (followRecipient == null || !followRecipient.isActive()) {
			response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username " + username);
			return;
		}
		Users follower = userRepository.findByCredentials_Username(credentials.getUsername());
		
		if (follower == null) {
			response.sendError(CredentialsNoMatchException.STATUS_CODE,"Credentials did not match");
			return;
		}
		
		List<Follower> follow = followerRepository.findAll();
		
		Following followRecipientFollow = new Following(followRecipient);
		Follower followerFollower = new Follower(follower);
		
		if (follow != null) {
			for (Follower temp : follow) {
				System.out.println(temp.getProfile().getId());
				System.out.println("2nd Param: " + follower.getProfile().getId());
				if (temp.getProfile().getId() == follower.getProfile().getId()) {
					response.sendError(FollowingException.STATUS_CODE, "Already following User: " + username);
					return;
				}
			}
		}
		
		followRecipientFollow.setUser(follower);
		followerFollower.setUser(followRecipient);
		
		follower.getFollowing().add(followingRepository.save(followRecipientFollow));
		followRecipient.getFollowers().add(followerRepository.save(followerFollower));
	}

	public void unfollow(String username, CredentialsDto credentials, HttpServletResponse response)
        throws IOException, UserDoesNotExistException, CredentialsNoMatchException, FollowingException {
    	
		Users unfollowRecipient = userRepository.findByUsernameIgnoreCase(username);
		
		if (unfollowRecipient == null || !unfollowRecipient.isActive()) {
			response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username " + username);
			return;
		}
		Users unfollower = userRepository.findByCredentials_Username(credentials.getUsername());
		if (unfollower == null) {
			response.sendError(CredentialsNoMatchException.STATUS_CODE,"Credentials did not match");
			return;
		}
		
		List<Following> following = unfollower.getFollowing();
		
		List<Follower> followers = unfollowRecipient.getFollowers();

        Following deleteFollowing = null;
        
        Follower deleteFollower = null;
        
		if (!following.isEmpty() || !followers.isEmpty()) {
			
			for (Following temp : following) {
				
				if (temp.getProfile().getId() == unfollowRecipient.getProfile().getId()) {
										
				    deleteFollowing = temp;
				}
			}
			for (Follower temp : followers) {
				
				if (temp.getProfile().getId() == unfollower.getProfile().getId()) {
					
					deleteFollower = temp;
					
				}
			}

			if (deleteFollowing != null && deleteFollower != null) {
			    following.remove(deleteFollowing);
			    followers.remove(deleteFollower);
			}
			deleteFollowing.setUser(null);
			deleteFollower.setUser(null);
			
			followingRepository.delete(deleteFollowing);
			followerRepository.delete(deleteFollower);
			
			return;
		}
		response.sendError(FollowingException.STATUS_CODE, "Already unfollowing User: " + username);	
	}

	public List<TweetDto> getTweets(String username, HttpServletResponse response) 
			throws IOException, UserDoesNotExistException {
		
		Users tweetUser = userRepository.findByUsernameIgnoreCase(username);

		UserDto tweetUserDto = userMapper.toDto(tweetUser);
		
		if (tweetUser == null || !tweetUser.isActive()) {
			response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username "
			        + username);
			    return null;
		}
		return tweetUser.getTweets().stream().map( tweetMapper :: toDto ).collect(Collectors.toList());

	}

	public List<TweetDto> getMentions(String username) {
		Users updateUser = userRepository.findByUsernameIgnoreCase(username);
        return null;
	}

	public List<FollowingDto> getFollowing(String username, HttpServletResponse response) 
			throws IOException, UserDoesNotExistException {
		Users followingUser = userRepository.findByUsernameIgnoreCase(username);
		
		if (followingUser == null || !followingUser.isActive()) {
			response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username "
		        + username);
		    return null;
		}
		return followingUser.getFollowing().stream().map( followingMapper::toDto ).collect(Collectors.toList());
	}

	public List<FollowerDto> getFollowers(String username, HttpServletResponse response) 
			throws IOException, UserDoesNotExistException {
		Users followerUser = userRepository.findByUsernameIgnoreCase(username);

		if (followerUser == null || !followerUser.isActive()) {
			response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username "
		        + username);
		    return null;
		}
		return followerUser.getFollowers().stream().map( followerMapper::toDto ).collect(Collectors.toList());
	}

}
