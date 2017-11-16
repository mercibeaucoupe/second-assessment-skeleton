package com.cooksys.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.twitter.tweets.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {

	public Hashtag findByLabel(String label);

	@Query(value = "SELECT label FROM hashtag", nativeQuery = true)
	public List<String> findLabel();

	public List<Hashtag> findByTweets_Id(Integer id);


}
