package idv.sam.springwebapp.service;

import java.util.List;
import idv.sam.springwebapp.model.User;
import idv.sam.springwebapp.model.UserLogin;

public interface UserManager {
	
	List<User> getAllUser();

	User getUserByFullname(String firstname, String lastname);
	
	User getUserByUsernameAndPassword(String username, String password);
	
	User getUserByEmailAndPassword(String email, String password);
	
	Boolean validateUser(String email, String password);

	UserLogin userLogin(String email, String password);
	
	Boolean usernameIsExists(String username);
	
	Boolean emailIsExists(String email);

	Boolean registerUser(User user);

	String createJWT(String username, String email);

	Boolean validateJWT(String clientJWTCookie);
	
}
