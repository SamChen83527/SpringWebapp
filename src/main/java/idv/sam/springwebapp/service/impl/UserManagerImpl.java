package idv.sam.springwebapp.service.impl;

import java.util.List;
import idv.sam.springwebapp.dao.UserDao;
import idv.sam.springwebapp.model.User;
import idv.sam.springwebapp.model.UserLogin;
import idv.sam.springwebapp.service.UserManager;

public class UserManagerImpl implements UserManager{
	/* Dependency Injection */
	private UserDao userDao;
	public UserManagerImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/* Login */
	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		
		User user = userDao.getUserByUsernameAndPassword(username, password);
		
		return user;
	}
	
	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		
		User user = userDao.getUserByEmailAndPassword(email, password);
		
		return user;
	}
	
	@Override
	public Boolean validateUser(String email, String password) {
		Boolean validation =  false;
		
		if (userDao.getUserByEmailAndPassword(email, password) != null ) {
			validation = true;
		}
		
		return validation;
	}
	
	@Override
	public UserLogin userLogin(String email, String password) {
		UserLogin userLogin = new UserLogin();
		// check user name
		if (userDao.countUserEmailNumber(email) == 1) {
			// check password
			if (userDao.validateUserPassword(email, password)) {
				// response user information
				userLogin.setLogin(userDao.getUserByEmailAndPassword(email, password));
				userLogin.setLoginStatus("VALID");
			} else {
				userLogin.setLoginStatus("INVALID_PASSWORD_ERROR");
			}
		} else {
			// Please register first.
			userLogin.setLoginStatus("INVALID_REGISTRATION_NOTEXIST");
		}
		return userLogin;
	}
	
	/* Registration */
	@Override
	public Boolean emailIsExists(String email) {
		int count = userDao.countUserEmailNumber(email);
		Boolean isExists = (!(count == 0)) ? true : false;
		
		return isExists;
	}
	
	@Override
	public Boolean usernameIsExists(String username) {
		int count = userDao.countUsernameNumber(username);
		Boolean isExists = (!(count == 0)) ? true : false;
		
		return isExists;
	}
	
	@Override
	public Boolean registerUser(User user) {		
		userDao.insert(user);
		
		int count = userDao.countUsernameNumber(user.getUsername());		
		Boolean isExists = (!(count == 0)) ? true : false;
		
		return isExists;		
	}
	
	
	/* Practice */
	@Override
	public User getUserByFullname(String firstname, String lastname) {		
		
		User user = userDao.getUserByFullname(firstname, lastname);
		
		return user;
	}

	@Override
	public List<User> getAllUser() {
		
		List<User> users = userDao.getAll();
		
		return users;
	}
	
}
