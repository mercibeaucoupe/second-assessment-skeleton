package com.cooksys.twitter.hashtag_function;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cooksys.twitter.repository.HashtagRepository;
import com.cooksys.twitter.tweets.Hashtag;
import com.cooksys.twitter.tweets.Tweet;

public class HashtagFunctions {

	public void getHashtags(Tweet tweet, String content, HashtagRepository hashtagRepository) {

		
		
		System.out.println(tweet.getHashTag());
	}

}
