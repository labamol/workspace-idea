package com.apimsa.labs.qk.user.domain.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import com.apimsa.labs.qk.user.domain.model.User;
import com.apimsa.labs.qk.user.repo.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class UserService {
	
	private Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Inject UserRepository userRepo;
	
	public List<User> findAllUsers() {
		return userRepo.findAllUsers();
	}

	public User findUserById(Long id) {
		return userRepo.findUserById(id);
	}

	
	public void updateUser(User user) {
		userRepo.updateUser(user);
	}

	
	public void createUser(User user) {
		userRepo.createUser(user);
	}

	public void deleteUser(Long id) {
		userRepo.deleteUser(id);;
	}
	

}
