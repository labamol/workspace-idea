package com.apimsa.labs.person.domain.service;

import java.util.List;

import com.apimsa.labs.person.domain.model.Person;

public interface PersonService {

	
	public Person findByPersonId(String personId);

	public String createPerson(Person person);	
	
	public String updatePerson(Person person, String personId);	
	
	public String deletePerson(String personId);	
	
}

