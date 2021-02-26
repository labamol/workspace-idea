 package com.apimsa.labs.address.domain.model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Transient;

@Document(collection="address")
public class Address extends BaseEntity{
	
	/*
	 * // by default mongo documents have a _id
	 * 
	 * @Id public ObjectId _id;
	 
	
	**/
	
	// mongo sequence used to generate the id. Used inside AddressModelListener
	@Transient // prevents this field from serializing to persist in the table.
    public static final String SEQUENCE_NAME = "address_sequence";
	
	/* commenting after adding BaseEntity
	 * @Id private long id;
	 */
	// Will use the Mongo listener events lifecycle to set the id when mapping POJOs to mongo documents for storage
	
	@JsonProperty(required = true)
	@Indexed(unique = true)
	private String addressId;
	
	@JsonProperty(required = true)
	@NotEmpty
	private String personId;
	
	@JsonProperty(required = false)
	private String addressLine1;
	
	@JsonProperty(required = false)
	private String addressLine2;
	
	@JsonProperty(required = true)
	@NotEmpty
	private String zipCode;

	@JsonProperty(required = true)
	private String city;
	
	@JsonProperty(required = true)
	@NotEmpty
	private String stateCode;
	
	@JsonProperty(required = true)
	@NotEmpty
	private String countryCode;
		

	public Address() {}
	
		
/*	
	public Address(ObjectId _id, String addressId, String personId, String addressLine1, String addressLine2,
			String zipCode, String city, String stateCode, String countryCode) {
		super();
		this._id = _id;
		this.addressId = addressId;
		this.personId = personId;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.zipCode = zipCode;
		this.city = city;
		this.stateCode = stateCode;
		this.countryCode = countryCode;
	}

*/


	public Address(String addressId, String personId, String addressLine1, String addressLine2, String zipCode,
			String city, String stateCode, String countryCode) {
		super();
		this.addressId = addressId;
		this.personId = personId;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.zipCode = zipCode;
		this.city = city;
		this.stateCode = stateCode;
		this.countryCode = countryCode;
	}



	/*
	 * // ObjectId needs to be converted to string public String get_id() { return
	 * _id.toHexString(); }
	 * 
	 * public void set_id(ObjectId _id) { this._id = _id; }
	 * 
	 */
	
	
	/* Commented after adding BaseEntity
	 * public long getId() { return id; }
	 * 
	 * public void setId(long id) { this.id = id; }
	 */
	    
	
	public String getAddressId() {
		return addressId;
	}



	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}



	public String getPersonId() {
		return personId;
	}



	public void setPersonId(String personId) {
		this.personId = personId;
	}



	public String getAddressLine1() {
		return addressLine1;
	}



	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}



	public String getAddressLine2() {
		return addressLine2;
	}



	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}



	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}



	public String getStateCode() {
		return stateCode;
	}



	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}


	public String getCountryCode() {
		return countryCode;
	}


	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}


	
	@Override
	public String toString() {
		return "Address [id=" + id + ", addressId=" + addressId + ", personId=" + personId + ", addressLine1="
				+ addressLine1 + ", addressLine2=" + addressLine2 + ", zipCode=" + zipCode + ", city=" + city
				+ ", stateCode=" + stateCode + ", countryCode=" + countryCode + "]";
	}
}