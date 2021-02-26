package com.apimsa.labs.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import com.apimsa.labs.person.config.CustomConfigProperties;
import com.apimsa.labs.person.domain.model.Person;
import com.apimsa.labs.person.repo.PersonRepository;

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
@SpringBootApplication
// to register the PersonRepository as a Redis Repository
@EnableRedisRepositories
// To create custom properties typically hierarchical that can be read from application.properties or yaml
@EnableConfigurationProperties(CustomConfigProperties.class)
public class PersonApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(PersonApplication.class, args);
	
	}
	
   
		
	}
