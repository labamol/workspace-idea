package com.apimsa.labs.qk.user.repo;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import com.apimsa.labs.qk.user.domain.model.User;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User>{
	
	public List<User> findAllUsers() {
		return listAll();
	}

	public User findUserById(Long id) {
		// return (List<User>)em.createNamedQuery("Users.findUserById").setParameter("userId", id).getResultList();
		User user = findById(id);
		return user;
	}

	@Transactional
	public void updateUser(User user) {
		User userToUpdate = findUserById(user.getId());
		//userToUpdate.setName(user.getName());
		//userToUpdate.setEmail(user.getEmail());
		if (userToUpdate != null) {
			persist(user);
		}
	}

	@Transactional
	public void createUser(User user) {
		persist(user);
		//return user.getId();
	}

	@Transactional
	public void deleteUser(Long id) {
		User u = findUserById(id);
		delete(u);
		flush();
	}
	
}


/*
 * Code without Panache ...
 * {

	
	@PersistenceContext(unitName = "userdb-punit", type = PersistenceContextType.TRANSACTION)
	
	//@PersistenceContext
	private EntityManager em;
	
	
	public List<User> findAllUsers() {
		return (List<User>)em.createNamedQuery("Users.findAllUsers").getResultList();
	}

	public User findUserById(Long id) {
		// return (List<User>)em.createNamedQuery("Users.findUserById").setParameter("userId", id).getResultList();
		User user = em.find(User.class, id);
		return user;
	}

	@Transactional
	public void updateUser(User user) {
		User userToUpdate = findUserById(user.getId());
		//userToUpdate.setName(user.getName());
		//userToUpdate.setEmail(user.getEmail());
		if (userToUpdate != null) {
			em.merge(user);
		}
	}

	@Transactional
	public void createUser(User user) {
		em.persist(user);
	}

	@Transactional
	public void deleteUser(Long id) {
		User u = findUserById(id);
		em.remove(u);
	}
	
}*/
