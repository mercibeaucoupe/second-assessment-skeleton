package com.cooksys.twitter.mapper;


import org.mapstruct.Mapper;

import com.cooksys.twitter.dto.UserDto;
import com.cooksys.twitter.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto toDto(Users user);
	
	Users fromDto(UserDto dto);

}