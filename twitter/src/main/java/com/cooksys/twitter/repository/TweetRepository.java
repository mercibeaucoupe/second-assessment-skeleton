package com.cooksys.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitter.tweets.Hashtag;
import com.cooksys.twitter.tweets.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Integer>{

	Tweet findById(Integer id);

	List<Tweet> findByhashTag_Id(Integer id);

}
