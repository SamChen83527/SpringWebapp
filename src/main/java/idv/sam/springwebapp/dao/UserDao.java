package idv.sam.springwebapp.dao;

import java.util.List;
import idv.sam.springwebapp.model.User;

/**
 * @author Sam
 * @param <T>
 */
public interface UserDao extends Dao<User>{
	// CREATE
    void insert(User t);	
    
    // READ ALL USERS
    List<User> getAll();
    
    // READ USER
    User getUserByUsername(String username);
    User getUserByUsernameAndPassword(String username, String password);
    User getUserByEmailAndPassword(String email, String password);
    User getUserByFullname(String firstname, String lastname);
    
    // COUNT USERNAME NUMBER
    Integer countUsernameNumber(String username);
    // COUNT USER EMAIL NUMBER
    Integer countUserEmailNumber(String email);
    // VALIDATE USER PASSWORD
    Boolean validateUserPassword(String email, String password);
    
    // UPDATE
    void update(User t);
    // UPDATE PASSWORD BY USERNAME
    void updateByUsername(String password, String username);
    
    // DELETE
    void delete(User t);
    // DELETE by Id
    void deleteById(long id);

	
}