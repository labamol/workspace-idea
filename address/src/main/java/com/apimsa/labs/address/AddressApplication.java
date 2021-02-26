package com.apimsa.labs.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.apimsa.labs.address.domain.model.Address;
import com.apimsa.labs.address.repo.AddressRepository;

import io.dekorate.kubernetes.annotation.Env;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Label;
import io.dekorate.kubernetes.annotation.Port;
import io.dekorate.option.annotation.JvmOptions;


// @KubernetesApplication is a more specialized form of @Dekorate. 
/** When mixing annotations and application.properties the latter will always 
 * take precedence overriding values that defined using annotations. 
 * This allows users to define the configuration using annotations and 
 * externalize configuration to application.properties.
 
@KubernetesApplication(replicas = 1,
		envVars = { @Env(name = "propertyEnv", value = "Hello from env!"),
		        @Env(name = "propertyFromMap", value = "property1", configmap = "sample-configmap") },
		expose = true,
		ports = @Port(name = "http", containerPort = 9082),
		labels = @Label(key = "version", value = "v1"))
		@JvmOptions(server = true, xmx = 256)
*/

@KubernetesApplication
//@EnableMongoRepositories("com.apimsa.labs.address.repo")
@SpringBootApplication
public class AddressApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(AddressApplication.class, args);
	
	}
	
	/*
	 * @Bean public CommandLineRunner sampleData(AddressRepository repository) {
	 * return (args) -> { repository.save(new Address("301", "202", "line1",
	 * "line2", "400016", "Mumbai", "MH", "IN")); repository.save(new Address("302",
	 * "201", "line1", "line2", "400093", "Mumbai", "MH", "IN"));
	 * repository.save(new Address("303", "202", "line1", "line2", "400072",
	 * "Mumbai", "MH", "IN")); }; }
	 */
		
	}
