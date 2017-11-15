package com.cooksys.twitter.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitter.entity.Follower;

public interface FollowerRepository extends JpaRepository<Follower, Integer> {

	void deleteById(Integer id);


}
