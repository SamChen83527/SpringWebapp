package idv.sam.springwebapp.service;

import java.util.List;
import idv.sam.springwebapp.model.User;

public interface UserManager {	
	
	String getUserEmailByUsername(String username);
	
	List<User> getAllUser();
	
	void insertUser(User user);

	User getUserByFullname(String firstname, String lastname);
	
	User getUserByUsernameAndPassword(String username, String password);
	
	Boolean validateUser(String username, String password);

	User userLogin(String username, String password);
}
