package com.cooksys.twitter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.twitter.dto.HashtagDto;
import com.cooksys.twitter.dto.TweetDto;
import com.cooksys.twitter.mapper.HashtagMapper;
import com.cooksys.twitter.repository.HashtagRepository;
import com.cooksys.twitter.repository.TweetRepository;

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

}
