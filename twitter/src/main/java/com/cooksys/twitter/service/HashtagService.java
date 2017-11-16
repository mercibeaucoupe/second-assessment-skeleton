package com.cooksys.twitter.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cooksys.twitter.dto.HashtagDto;
import com.cooksys.twitter.dto.TweetDto;
import com.cooksys.twitter.mapper.HashtagMapper;
import com.cooksys.twitter.repository.HashtagRepository;
import com.cooksys.twitter.repository.TweetRepository;
import com.cooksys.twitter.tweets.Hashtag;
import com.cooksys.twitter.tweets.Tweet;

@Service
public class HashtagService {
	
	HashtagRepository hashtagRepository;
	TweetRepository tweetRepository;
	
	HashtagMapper hashtagMapper;
	
	public HashtagService(HashtagRepository hashtagRepository, 
			HashtagMapper hashtagMapper) {
		
		this.hashtagRepository = hashtagRepository;
		this.hashtagMapper = hashtagMapper;
	}

	public List<TweetDto> getAllByLabel(String label) {
		return null;
	}

	public List<HashtagDto> getAll() {
		return hashtagRepository.findAll().stream().map(hashtagMapper::toDto ).collect(Collectors.toList());
	}

	public boolean hasLabel(String label) {
		return hashtagRepository.findByLabel(label) != null;
	}
	
	@Transactional
	public Hashtag createTag(String label) {
		Hashtag creatingTag = hashtagRepository.findByLabel(label);
		if (creatingTag == null) {
			return hashtagRepository.save(
					new Hashtag(label,
					new Timestamp(System.currentTimeMillis()).getTime(),
					new Timestamp(System.currentTimeMillis()).getTime()));			
		} else {
		    creatingTag.setLastUsed(new Timestamp(System.currentTimeMillis()).getTime());
		}
		return null;
	}

}
