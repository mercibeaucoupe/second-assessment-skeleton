package com.cooksys.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitter.tweets.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Integer>{

	Tweet findById(Integer id);

}
