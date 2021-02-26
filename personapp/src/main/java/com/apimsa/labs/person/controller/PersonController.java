package com.apimsa.labs.person.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apimsa.labs.person.domain.model.Person;
import com.apimsa.labs.person.domain.service.PersonService;



@RestController
// set baseUrl of API 
@RequestMapping("/person")
public class PersonController {

	@Autowired
    private PersonService service;

    //@RequestMapping(value = "/{personId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findByPersonId(@PathVariable(value="personId") String personId) {
      return service.findByPersonId(personId);
    } 
    

    //@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addPerson(@Valid @RequestBody Person person) {
      return service.createPerson(person);
    } 
	
	
	 //@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		@PutMapping(value = "/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
	    public String updatePerson(@Valid @RequestBody Person person, @PathVariable(value="personId") String personId) {
			/*
			 * if(personId == null) { return Exception; }
			 */
			return service.updatePerson(person, personId);
		}
	
}
