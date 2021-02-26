package com.apimsa.labs.address.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.apimsa.labs.address.domain.model.Address;
 


public interface AddressRepository extends MongoRepository<Address, String> {
	
	@Query("{addressId: '?0'}")
	public Address findByAddressId(String addressId);

	@Query("{personId: '?0'}")
	public List<Address> findByPersonId(String personId);
}

