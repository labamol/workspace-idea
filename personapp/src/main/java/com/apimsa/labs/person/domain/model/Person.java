package com.apimsa.labs.person.domain.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


// Dependency in pom brings in 2 Spring Data modules — Spring Data Redis and Spring Data Key Value
// So Spring doesn’t know whether to save the object in Redis or KeyValue store. 
// By adding @RedisHash on Person class, we are telling Spring to save it in Redis
@RedisHash(value = "person")
public class Person implements Serializable {

	@Id
	private String personId;
	@Indexed // Creates an index on Redis for the annotated field
	private String personName;
	
	private String city;

		

	public Person() {}


	public Person(String personId, String personName, String city) {
		super();
		this.personId = personId;
		this.personName = personName;
		this.city = city;
	}


	/*
	 * // ObjectId needs to be converted to string public String get_id() { return
	 * _id.toHexString(); }
	 * 
	 * public void set_id(ObjectId _id) { this._id = _id; }
	 */
	


	
	public String getPersonId() {
		return personId;
	}


	public void setPersonId(String personId) {
		this.personId = personId;
	}


	public String getPersonName() {
		return personName;
	}


	public void setPersonName(String personName) {
		this.personName = personName;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	@Override
	public String toString() {
		//return "Person [_id=" + _id + ", personId=" + personId + ", personName=" + personName + ", city=" + city + "]";
		return "Person [" + ", personId=" + personId + ", personName=" + personName + ", city=" + city + "]";
	}
	
	
	
}
