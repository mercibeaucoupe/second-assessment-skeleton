package com.cooksys.twitter.service;

import org.springframework.stereotype.Service;

import com.cooksys.twitter.repository.HashtagRepository;

@Service
public class HashtagService {
	
	HashtagRepository hashTagRepository;
	
	public HashtagService(HashtagRepository hashTagRepository) {
		this.hashTagRepository = hashTagRepository;
	}

	public boolean getAllByLabel(String label) {
		return !hashTagRepository.findByLabel(label).isEmpty();
	}

}
