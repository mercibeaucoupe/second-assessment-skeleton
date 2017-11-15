package com.cooksys.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitter.dto.ReplyDto;
import com.cooksys.twitter.tweets.Reply;

@Mapper(componentModel = "spring")
public interface ReplyMapper {

	ReplyDto toDto(Reply reply);
	
	Reply fromDto(ReplyDto dto);

}
