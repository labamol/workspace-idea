package com.apimsa.labs.address.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apimsa.labs.address.domain.model.Address;
import com.apimsa.labs.address.domain.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
// set baseUrl of API to /address
@RequestMapping("/api/address")
public class AddressController {

	@Autowired
    private AddressService service;
	
	private final static Logger logger = LoggerFactory.getLogger(AddressController.class);

	private static final String ID = "addressId";
	private static final String NEW_ADDRESS_LOG = "New address was created with id:{}";
	private static final String ADDRESS_UPDATED_LOG = "Address:{} was updated";
	
    //@RequestMapping(value = "/{addressId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "/{addressId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> findByAddressId(@PathVariable(value="addressId") String addressId) {
			Optional<Address> address = service.findByAddressId(addressId);
    		if (address.isEmpty()) {
    			logger.info("AddressController::findByAddressId - Address id not found :" + addressId );
    			return ResponseEntity.notFound().build();
    		}
    		else {
    			logger.info("AddressController::findByAddressId" + address.get().toString() );
    			return ResponseEntity.ok().body(address.get());
    		}
    } 
    
	
    //@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Address>> findAllAddresses() {
		return ResponseEntity.ok(service.findAllAddresses());
    } 
	
    //@RequestMapping(value = "/person/{personId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "/person/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Address>> findByPersonId(@PathVariable(value="personId") String personId) {
      return ResponseEntity.ok(service.findByPersonId(personId));
    } 

    //@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED) // checking in Controller Test class
    public ResponseEntity<Address> addAddress(@Valid @RequestBody Address address) {
      Address createdAddress = service.createAddress(address);
      logger.info(NEW_ADDRESS_LOG, createdAddress.getId());
      return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    } 
	
}
