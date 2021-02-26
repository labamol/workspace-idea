package com.apimsa.labs.address.domain.service;


import java.util.List;
import java.util.Optional;

import com.apimsa.labs.address.domain.model.Address;

public interface AddressService {
	
	public List<Address> findAllAddresses();
	public Optional<Address> findByAddressId(String addressId);
	public List<Address> findByPersonId(String customerId);
	public Address createAddress(Address address);	

}
