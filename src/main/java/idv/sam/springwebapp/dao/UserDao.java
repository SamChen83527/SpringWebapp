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
	
	// READ
    User getById(long id);
    // READ BY USERNAME
    User getByUsername(String username);
    // READ BY USERNAME AND PASSWORD
    User getByUsernameAndPassword(String username, String password);
    // READ BY FULLNAME
    User getByFullname(String firstname, String lastname);
    // READ ALL
    List<User> getAll();
    
    // UPDATE
    void update(User t);
    // UPDATE PASSWORD BY USERNAME
    void updateByUsername(String password, String username);
    
    // DELETE
    void delete(User t);
    // DELETE by Id
    void deleteById(long id);
}