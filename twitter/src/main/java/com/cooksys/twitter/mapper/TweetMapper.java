package com.cooksys.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitter.dto.TweetDto;
import com.cooksys.twitter.tweets.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {
	
	TweetDto toDto(Tweet tweet);
	
	Tweet fromDto(TweetDto dto);

}
