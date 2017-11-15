package com.cooksys.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitter.dto.SimpleTweetDto;
import com.cooksys.twitter.tweets.SimpleTweet;

@Mapper(componentModel = "spring")
public interface SimpleTweetMapper {

	SimpleTweetDto toDto(SimpleTweet simpleTweet);

	SimpleTweet fromDto(SimpleTweetDto dto);

}
