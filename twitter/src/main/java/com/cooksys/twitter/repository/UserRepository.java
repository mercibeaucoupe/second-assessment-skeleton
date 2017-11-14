package com.cooksys.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitter.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);

	public List<User> findByActiveTrue();

	public User findByCredentials_Username(String username);
}
