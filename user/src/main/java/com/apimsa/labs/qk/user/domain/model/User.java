package com.apimsa.labs.qk.user.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "users")
//@NamedQuery(name = "Users.findAllUsers",	query = "SELECT u FROM Users u")
//@NamedQuery(name = "Users.findUserById",	query = "SELECT u FROM Users u WHERE u.id = :userId")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	//Validation annotations require hibernate validator in POM
	@NotBlank(message = "name cannot be blank")
	private String name;
	@NotBlank(message = "email cannot be blank")
	private String email;
	
	protected User() {
	}
	
	public User(Long id, String name, String email) {
	this.id = id;
	this.name = name;
	this.email = email;
	}

	public User(String name, String email) {
		super();
		this.name = name;
		this.email = email;
		}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return String.format("User[Id=%d, Name='%s', Email='%s'']", this.id,
				this.name, this.email);
	}
	
	
}
