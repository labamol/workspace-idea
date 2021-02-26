package com.apimsa.labs.qk.user.config;

import java.util.Optional;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix="userapiconfig")
public class UserConfiguration {

	private String apiversion; // use same case as provided in application.yaml
	private Optional<String> author;

	public String getApiversion() {
		return apiversion;
	}

	public void setApiversion(String apiversion) {
		this.apiversion = apiversion;
	}

	public Optional<String> getAuthor() {
		return author;
	}

	public void setAuthor(Optional<String> author) {
		this.author = author;
	}
	
	
}
