package com.cooksys.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitter.entity.Credentials;

public interface CredentialRepository extends JpaRepository<Credentials, Integer> {

	public Credentials findByUsername(String username);
}
