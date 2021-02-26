package com.apimsa.labs.address.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.apimsa.labs.address.domain.model.Address;
import com.apimsa.labs.address.repo.AddressRepository;

@Service
//@EnableMongoRepositories("com.apimsa.labs.address.repo")
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepository addressRepository;
	
	private static final Logger logger = Logger.getLogger(AddressServiceImpl.class.getName());
	
	@Override
	public Optional<Address> findByAddressId(String addressId) {
		logger.info("AddressServiceImpl::findByAddressId: Address db has " + addressRepository.count() + "entries");
		// Optional.ofNullable(obj) will return Optional with either a non-null obj or an empty
		// Checking for Optional.isEmpty in Controller 
		return Optional.ofNullable(addressRepository.findByAddressId(addressId)); 
		
	}

	@Override
	public Address createAddress(Address address) {
		logger.info("AddressServiceImpl::createAddress: Saving address : " + address.toString());
		addressRepository.save(address);
		logger.info("AddressServiceImpl::createAddress: Saved address : " + address.toString());
		return address;
	}

	@Override
	public List<Address> findByPersonId(String personId) {
		System.out.println("AddressServiceImpl::findByPersonId: Address db has " + addressRepository.count() + "entries");
		return addressRepository.findByPersonId(personId);
	}

	@Override
	public List<Address> findAllAddresses() {
		return addressRepository.findAll();
	}

}
