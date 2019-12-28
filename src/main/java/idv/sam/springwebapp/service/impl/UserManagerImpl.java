package idv.sam.springwebapp.service.impl;

import java.util.List;
import idv.sam.springwebapp.dao.UserDao;
import idv.sam.springwebapp.model.User;
import idv.sam.springwebapp.service.UserManager;

public class UserManagerImpl implements UserManager{

	private UserDao userDao;
	
	/* Dependency Injection */
	public UserManagerImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/* Login */
	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		
		User user = userDao.getByUsernameAndPassword(username, password);
		
		return user;
	}
	
	@Override
	public Boolean validateUser(String username, String password) {
		Boolean validation =  false;
		
		if (userDao.getByUsernameAndPassword(username, password) != null ) {
			validation = true;
		}
		
		return validation;
	}
	
	/* Registration */
	@Override
	public void insertUser(User user) {
		
		userDao.insert(user);
		
	}
	
	
	/* Practice */
	@Override
	public String getUserEmailByUsername(String username) {		
		
		User user = userDao.getByUsername(username);
		
		return user.getEmail();
	}
	
	@Override
	public User getUserByFullname(String firstname, String lastname) {		
		
		User user = userDao.getByFullname(firstname, lastname);
		
		return user;
	}

	@Override
	public List<User> getAllUser() {
		
		List<User> users = userDao.getAll();
		
		return users;
	}
	
}
