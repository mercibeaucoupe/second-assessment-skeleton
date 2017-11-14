package com.cooksys.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitter.tweets.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {

	public List<Hashtag> findByLabel(String label);

}
