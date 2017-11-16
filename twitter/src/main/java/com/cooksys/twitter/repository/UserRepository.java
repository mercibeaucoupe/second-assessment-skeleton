package com.cooksys.twitter.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.twitter.entity.Following;
import com.cooksys.twitter.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	public Users findByUsername(String username);

	public List<Users> findByActiveTrue();

	public Users findByCredentials_Username(String username);

	public Users findById(Integer id);

	public Users findByUsernameIgnoreCase(String username);

	public Object findByUsernameIgnoreCaseAndActiveTrue(String username);

	public Users findByUsernameAndActiveTrue(String username);
	
	public List<Following> findFollowing_IdById(Integer id);
	
	@Query(value = "SELECT COUNT(*) FROM users_following", nativeQuery = true)
	public int checkTableIsNull();
	
}