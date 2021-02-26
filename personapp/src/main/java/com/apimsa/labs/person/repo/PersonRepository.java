package com.apimsa.labs.person.repo;

import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apimsa.labs.person.domain.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, String> {

	}
	
