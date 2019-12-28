package idv.sam.springwebapp.dao;

import java.util.List;

/**
 *
 * @author Sam
 * @param <T>
 */
public interface Dao<T> {
	// CREATE
    void insert(T t);
	
	// READ
    T getById(long id);
    // READ ALL
    List<T> getAll();
    
    // UPDATE
    void update(T t);
    
    // DELETE
    void delete(T t);
    // DELETE by Id
    void deleteById(long id);
}