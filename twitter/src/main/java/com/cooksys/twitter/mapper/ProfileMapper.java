package com.cooksys.twitter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.twitter.dto.ProfileDto;
import com.cooksys.twitter.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    
	ProfileDto toDto(Profile profile);
	
	Profile fromDto(ProfileDto dto);
}
