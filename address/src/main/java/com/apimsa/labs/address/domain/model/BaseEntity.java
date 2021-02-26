package com.apimsa.labs.address.domain.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BaseEntity {

	@JsonProperty(required = true)
	@Id
	/*
	 * @GeneratedValue(generator = "custom-generator", strategy =
	 * GenerationType.IDENTITY)
	 * 
	 * @GenericGenerator( name = "custom-generator", strategy =
	 * "com.data.model.id.generator.BaseIdentifierGenerator")
	 */
	protected long id;

	@JsonProperty(required = false) // may be skipped in the json payloads
	protected Instant createdAt;

	@JsonProperty(required = false)
	protected Instant modifiedAt;

	@JsonProperty(required = false)
	protected int version;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Instant modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ""+ id;
	}
}