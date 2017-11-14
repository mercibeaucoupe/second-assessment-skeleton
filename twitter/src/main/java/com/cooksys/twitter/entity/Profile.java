package com.cooksys.twitter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Profile {
	
	@Id
	@GeneratedValue
	private Integer id;

	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	
	@OneToOne
	private User user;
	
	public Profile(String email) {
		this.email = email;
		this.firstName = null;
		this.lastName = null;
		this.phone = null;
	}
	
	public Profile(String firstName, String email) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = null;
		this.phone = null;
	}
	
	public Profile(String firstName, String lastName, String email) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = null;
	}
	
	public Profile(String firstName, String lastName, String email, String phone) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}
	
	public Profile() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
