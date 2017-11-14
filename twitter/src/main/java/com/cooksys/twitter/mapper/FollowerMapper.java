package com.cooksys.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitter.dto.FollowerDto;
import com.cooksys.twitter.entity.Follower;

@Mapper(componentModel = "spring")
public interface FollowerMapper {
    
	FollowerDto toDto(Follower follower);
	
	Follower fromDto(FollowerDto dto);
	
}