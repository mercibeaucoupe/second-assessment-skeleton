package com.cooksys.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitter.dto.FollowingDto;
import com.cooksys.twitter.entity.Following;

@Mapper(componentModel = "spring")
public interface FollowingMapper {

	FollowingDto toDto(Following following);
	
	Following fromDto(FollowingDto dto);
	
}
