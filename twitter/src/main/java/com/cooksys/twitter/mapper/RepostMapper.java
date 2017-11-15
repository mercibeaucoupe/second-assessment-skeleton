package com.cooksys.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitter.dto.RepostDto;
import com.cooksys.twitter.tweets.Repost;

@Mapper(componentModel = "spring")
public interface RepostMapper {

	RepostDto toDto(Repost repost);
	
	Repost fromDto(RepostDto dto);

}
