package com.cooksys.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitter.dto.CredentialsDto;
import com.cooksys.twitter.entity.Credentials;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

	CredentialsDto toDto(Credentials credentials);
	
	Credentials fromDto(CredentialsDto dto);
	
}