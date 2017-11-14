package com.cooksys.twitter.mapper;


import org.mapstruct.Mapper;

import com.cooksys.twitter.dto.UserDto;
import com.cooksys.twitter.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto toDto(User user);
	
	User fromDto(UserDto dto);

}