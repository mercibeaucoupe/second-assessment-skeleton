package com.cooksys.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitter.dto.HashtagDto;
import com.cooksys.twitter.tweets.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

	HashtagDto toDto(Hashtag hashtag);
	
	Hashtag fromDto(HashtagDto dto);

}
