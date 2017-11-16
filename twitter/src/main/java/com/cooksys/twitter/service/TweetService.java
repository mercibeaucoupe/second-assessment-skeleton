package com.cooksys.twitter.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter.dto.UserDto;
import com.cooksys.twitter.entity.Users;
import com.cooksys.twitter.exception.CredentialsNoMatchException;
import com.cooksys.twitter.exception.TweetDoesNotExistException;
import com.cooksys.twitter.exception.UserDoesNotExistException;
import com.cooksys.twitter.helper.ContentCredentials;
import com.cooksys.twitter.mapper.CredentialsMapper;
import com.cooksys.twitter.mapper.HashtagMapper;
import com.cooksys.twitter.mapper.ReplyMapper;
import com.cooksys.twitter.mapper.RepostMapper;
import com.cooksys.twitter.mapper.SimpleTweetMapper;
import com.cooksys.twitter.mapper.TweetMapper;
import com.cooksys.twitter.mapper.UserMapper;
import com.cooksys.twitter.repository.HashtagRepository;
import com.cooksys.twitter.repository.TweetRepository;
import com.cooksys.twitter.repository.UserRepository;
import com.cooksys.twitter.tweets.Hashtag;
import com.cooksys.twitter.tweets.Reply;
import com.cooksys.twitter.tweets.Repost;
import com.cooksys.twitter.tweets.SimpleTweet;
import com.cooksys.twitter.tweets.Tweet;
import com.cooksys.twitter.dto.CredentialsDto;
import com.cooksys.twitter.dto.HashtagDto;
import com.cooksys.twitter.dto.ReplyDto;
import com.cooksys.twitter.dto.RepostDto;
import com.cooksys.twitter.dto.SimpleTweetDto;
import com.cooksys.twitter.dto.TweetDto;

@Service
public class TweetService {

	HashtagService hashtagService;
	
	TweetRepository tweetRepository;
	UserRepository userRepository;
	HashtagRepository hashtagRepository;
	
	TweetMapper tweetMapper;
	CredentialsMapper credentialsMapper;
	ReplyMapper replyMapper;
	RepostMapper repostMapper;
	HashtagMapper hashtagMapper;
	UserMapper userMapper;
	SimpleTweetMapper simpleTweetMapper;
		
	public TweetService(HashtagService hashtagService,
			TweetRepository tweetRepository,
			UserRepository userRepository,
			HashtagRepository hashtagRepository,
			TweetMapper tweetMapper, 
			CredentialsMapper credentialsMapper,
			ReplyMapper replyMapper,
			RepostMapper repostMapper,
			HashtagMapper hashtagMapper,
			UserMapper userMapper,
			SimpleTweetMapper simpleTweetMapper) {
		
		this.hashtagService = hashtagService;
		
		this.tweetRepository = tweetRepository;
		this.userRepository = userRepository;
		this.hashtagRepository = hashtagRepository;
		
		this.tweetMapper = tweetMapper;
		this.credentialsMapper = credentialsMapper;
		this.replyMapper = replyMapper;
		this.repostMapper = repostMapper;
		this.userMapper = userMapper;
		this.simpleTweetMapper = simpleTweetMapper;
		
	}
	
	public List<TweetDto> getAll() {
		return tweetRepository.findAll().stream()
				.filter(tweet -> tweet.isActive())
				.map( tweetMapper::toDto ).collect(Collectors.toList());
	}

	public TweetDto get(Integer id, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException {
		
		Tweet tweet = tweetRepository.findById(id);
		if (tweet == null || !tweet.isActive()) {
			response.sendError(TweetDoesNotExistException.STATUS_CODE, "Tweet with the ID: " 
		            + id + " does not exist");
			
			return null;
		}
		return tweetMapper.toDto(tweet);
		
	}

	@Transactional
	public TweetDto delete(Integer id, CredentialsDto credentials, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException, CredentialsNoMatchException {


		Tweet deleteTweet = tweetRepository.findById(id);
		if (deleteTweet == null || !deleteTweet.isActive()) {
			response.sendError(TweetDoesNotExistException.STATUS_CODE, "Tweet with the ID: " 
			        + id + " does not exist");
			
			return null;
		}
		CredentialsDto deleteCredentials = credentialsMapper.toDto(deleteTweet.getAuthor().getCredentials());
		if (!deleteCredentials.equals(credentials)) {
			response.sendError(CredentialsNoMatchException.STATUS_CODE, "Credentials did not match");
			return null;
		}
		
		deleteTweet.setActive(false);
		
		return tweetMapper.toDto(deleteTweet);
		
	}

	@Transactional
	public void like(Integer id, CredentialsDto credentials, HttpServletResponse response) 
			throws IOException {
		Tweet likedTweet = tweetRepository.findById(id);
		if (likedTweet == null || !likedTweet.isActive()) {
			response.sendError(TweetDoesNotExistException.STATUS_CODE, "Tweet with the ID: " 
			        + id + " does not exist");
			
			return;
		}
		Users liker = userRepository.findByUsername(credentials.getUsername());
		if (liker == null) {
			response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username "
			        + credentials.getUsername());
				
			return;
		}
		
		if (!credentialsMapper.toDto(liker.getCredentials()).equals(credentials)) {
			response.sendError(CredentialsNoMatchException.STATUS_CODE, "Credentials did not match");
			return;
		}
		
		liker.getLiked().add(likedTweet);
		likedTweet.getLikes().add(liker);

	}

	@Transactional
	public TweetDto reply(Integer id, ContentCredentials user, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException, CredentialsNoMatchException {
		Tweet replyTo =  tweetRepository.findById(id);
		
		if (replyTo == null || !replyTo.isActive()) {
			response.sendError(TweetDoesNotExistException.STATUS_CODE, "Tweet with the ID: " 
			        + id + " does not exist");
			
			return null;
		}
		Users replier = userRepository.findByUsername(user.getCredentials().getUsername());
		if (replier == null) {
			response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username "
			        + user.getCredentials().getUsername());
				
				return null;
		}
		
		if (!credentialsMapper.toDto(replier.getCredentials()).equals(user.getCredentials())) {
			response.sendError(CredentialsNoMatchException.STATUS_CODE, "Credentials did not match");
			return null;
		}
		
		Tweet replyTweet = new Reply(replier, new Timestamp(
				System.currentTimeMillis()).getTime(), user.getContent(), replyTo);
		replier.getTweets().add(replyTweet);
		
		replyTweet.setRepostOf(replyTo);
		Pattern hashtagPattern = Pattern.compile("#(\\S+)");
		Matcher matcher = hashtagPattern.matcher(user.getContent());

		List<Hashtag> hashtagCompare = new ArrayList<>();

		List<Hashtag> allLabels = hashtagRepository.findAll();

		while(matcher.find()) {	
			hashtagCompare.add(
				new Hashtag(matcher.group(1),
					new Timestamp(System.currentTimeMillis()).getTime(),
				    new Timestamp(System.currentTimeMillis()).getTime()));
		}
		
		for (Hashtag compareTag : hashtagCompare) {
			for (Hashtag savedTag : allLabels) {
				if (compareTag.getLabel().equals(savedTag.getLabel())) {
					savedTag.setLastUsed(replyTweet.getPosted());
					hashtagCompare.remove(compareTag);
				}
			}
		}
		replyTweet.setHashTag(hashtagCompare);
		System.out.println(replyTweet.getHashTag().iterator().next().getLabel());
        hashtagRepository.save(hashtagCompare);
        
        Pattern mentionPattern = Pattern.compile("@(\\S+)");
		Matcher mentionMatcher = mentionPattern.matcher(user.getContent());
        List<String> mentionsCompare = new ArrayList<>();

		while(mentionMatcher.find()) {	
			mentionsCompare.add(mentionMatcher.group(1));
		}
		for (String temp: mentionsCompare) {
		    Users mentionedUser = userRepository.findByUsername(temp.substring(1));
		    if ( mentionedUser != null ) {
		    	mentionedUser.getMentions().add(replyTweet);
		    	replyTweet.getMentioned().add(mentionedUser);
		    }
		}

		return tweetMapper.toDto(tweetRepository.save(replyTweet));
	}
	
    @Transactional
	public TweetDto repost(Integer id, CredentialsDto user, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException {
		Tweet repostTo = tweetRepository.findById(id);
		
		if (repostTo == null || !repostTo.isActive()) {
			response.sendError(TweetDoesNotExistException.STATUS_CODE, "Tweet with the ID: " 
			        + id + " does not exist");
			
			return null;
		}
		Users reposter = userRepository.findByUsernameAndActiveTrue(user.getUsername());
		if (reposter == null) {
			response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username "
		        + user.getUsername());
			
			return null;
		}
		if (!credentialsMapper.toDto(reposter.getCredentials()).equals(user)) {
			response.sendError(CredentialsNoMatchException.STATUS_CODE, "Credentials did not match");
			return null;
		}
		
		Tweet repostTweet = new Repost(reposter, new Timestamp(System.currentTimeMillis()).getTime(), 
				repostTo);
		reposter.getTweets().add(repostTweet);
		repostTweet.setRepostOf(repostTo);
		
		return tweetMapper.toDto(tweetRepository.save(repostTweet));
	}

	public List<HashtagDto> getTags(Integer id, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException {
		Tweet tweetTags = tweetRepository.findById(id);
		if (tweetTags == null || !tweetTags.isActive()) {
			response.sendError(TweetDoesNotExistException.STATUS_CODE, "Tweet with the ID: " 
			        + id + " does not exist");
			
			return null;
		}
		return tweetTags.getHashTag().stream().map( hashtagMapper::toDto ).collect(Collectors.toList());
	}

	public List<UserDto> getLikes(Integer id, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException {
		Tweet likedTweets = tweetRepository.findById(id);
		if (likedTweets == null || !likedTweets.isActive()) {
			response.sendError(TweetDoesNotExistException.STATUS_CODE, "Tweet with the ID: " 
			        + id + " does not exist");
			
			return null;
		}
		return likedTweets.getLikes().stream().map( userMapper::toDto ).collect(Collectors.toList());
	}

	public List<ReplyDto> getReplies(Integer id, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserDto> getMentions(Integer id, HttpServletResponse response) 
			throws IOException, TweetDoesNotExistException {
		
		Tweet mentions = tweetRepository.findById(id);
		
		if (mentions == null || !mentions.isActive()) {
			response.sendError(TweetDoesNotExistException.STATUS_CODE, "Tweet with the ID: " 
			        + id + " does not exist");
			
			return null;
		}
		return mentions.getMentioned().stream().map( userMapper::toDto ).collect(Collectors.toList());
	}

	@Transactional
	public TweetDto post(ContentCredentials contentCredentials, HttpServletResponse response) 
			throws IOException, UserDoesNotExistException, CredentialsNoMatchException {

        Users poster = userRepository.findByUsernameAndActiveTrue(contentCredentials.getCredentials().getUsername());
         
        if (poster == null) {
            response.sendError(UserDoesNotExistException.STATUS_CODE, "Could not find User with username "
     		        + contentCredentials.getCredentials().getUsername());
     			
     		return null;
        }
         
        if ( !credentialsMapper.toDto(poster.getCredentials()).equals(contentCredentials.getCredentials()) ) {
        	response.sendError(CredentialsNoMatchException.STATUS_CODE, "Credentials did not match");
 			return null;
        }

        Tweet newTweet = new SimpleTweet(poster, contentCredentials.getContent(),
        		new Timestamp(System.currentTimeMillis()).getTime() );
        poster.getTweets().add(newTweet);
        
        Pattern hashtagPattern = Pattern.compile("#(\\S+)");
		Matcher matcher = hashtagPattern.matcher(contentCredentials.getContent());

		List<Hashtag> hashtagCompare = new ArrayList<>();

		List<Hashtag> allLabels = hashtagRepository.findAll();

		while(matcher.find()) {	
			hashtagCompare.add(
				new Hashtag(matcher.group(1),
					new Timestamp(System.currentTimeMillis()).getTime(),
				    new Timestamp(System.currentTimeMillis()).getTime()));
		}
		
		for (Hashtag compareTag : hashtagCompare) {
			for (Hashtag savedTag : allLabels) {
				if (compareTag.getLabel().equals(savedTag.getLabel())) {
					savedTag.setLastUsed(newTweet.getPosted());
					hashtagCompare.remove(compareTag);
				}
			}
		}
		newTweet.setHashTag(hashtagCompare);
		System.out.println(newTweet.getHashTag().iterator().next().getLabel());
        hashtagRepository.save(hashtagCompare);
        
        Pattern mentionPattern = Pattern.compile("@(\\S+)");
		Matcher mentionMatcher = mentionPattern.matcher(contentCredentials.getContent());
        List<String> mentionsCompare = new ArrayList<>();

		while(mentionMatcher.find()) {	
			mentionsCompare.add(mentionMatcher.group(1));
		}
		for (String temp: mentionsCompare) {
		    Users mentionedUser = userRepository.findByUsername(temp.substring(1));
		    if ( mentionedUser != null ) {
		    	mentionedUser.getMentions().add(newTweet);
		    	newTweet.getMentioned().add(mentionedUser);
		    }
		}
        return tweetMapper.toDto(tweetRepository.save(newTweet));
    }

}
