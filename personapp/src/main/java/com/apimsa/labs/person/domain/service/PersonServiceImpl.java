package com.apimsa.labs.person.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.apimsa.labs.person.domain.model.Person;
import com.apimsa.labs.person.repo.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;
	
	@Override
	public Person findByPersonId(String personId) {
		Optional<Person> object = personRepository.findById(personId);
		if (!(object.isEmpty())) {
			return object.get();
		}
		else
			return null;		
	}

	@Override
	public String createPerson(Person person) {
		personRepository.save(person);
		return null;
	}
	
	@Override
	public String updatePerson(Person person, String personId) {
		personRepository.save(person);
		return null;
	}
	

	@Override
	public String deletePerson(String personId) {
		personRepository.deleteById(personId);
		return null;
	}
}
