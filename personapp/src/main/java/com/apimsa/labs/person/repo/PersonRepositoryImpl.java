package com.apimsa.labs.person.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.apimsa.labs.person.domain.model.Person;


// Alternate way of implementing a Repository without using default 
// implementation by simply extending CrudRepository
// This would allow us to implement our own custom methods accessing the cache.


@Repository
// public class PersonRepositoryImpl implements PersonRepository {
public class PersonRepositoryImpl  {

	
	/*
	 * To support various operations on different datatypes, RedisTemplate provides
	 * operation classes like ValueOperations, ListOperations, SetOperations,
	 * HashOperations, StreamOperations, etc. RedisTemplate<K,V> i.e. Key and Value
	 */
	private RedisTemplate<String, Person> redisTemplate;
    private HashOperations hashOperations; //to access Redis cache
    private String key = "PERSON";
 
    public PersonRepositoryImpl(RedisTemplate redisTemplate) {
    	this.redisTemplate = redisTemplate;
    	this.hashOperations = redisTemplate.opsForHash();
    }
    
    
	/*
	 * @Override public Iterable findAll() { return
	 * hashOperations.entries("PERSON"); }
	 */

	public void save(Person person) {
		// put(key, hashkey, value)
		hashOperations.put(key,person.getPersonId(),person);
	}


	public Person findById(String id) {
		return (Person)hashOperations.get(key,id); // Person class must be serializable
	}


	public void update(Person person) {
		save(person);

	}

	public void delete(String id) {
		hashOperations.delete(key,id);

	}

	public List findAll(){
        return hashOperations.values(key);
    }
	
	
	
	public Map<String, List<Person>> multiGetPersons(List<String> personIds){
		Map<String, List<Person>> personMap = new HashMap<>();
		List<Object> persons = hashOperations.multiGet(key, personIds);
		for (int i = 0; i < personIds.size(); i++) {
			personMap.put(personIds.get(i), (List<Person>) persons.get(i));
		}
		return personMap;
	}
}
