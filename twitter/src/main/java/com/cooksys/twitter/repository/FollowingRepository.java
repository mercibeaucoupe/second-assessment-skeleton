package com.cooksys.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitter.entity.Following;


public interface FollowingRepository extends JpaRepository<Following, Integer> {

	void deleteById(Integer id);

}
