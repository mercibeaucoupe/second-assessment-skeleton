package com.cooksys.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitter.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
