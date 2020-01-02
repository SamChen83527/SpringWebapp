package idv.sam.springwebapp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import idv.sam.springwebapp.dao.UserDao;
import idv.sam.springwebapp.model.User;

//@Repository
public class UserDaoImpl implements UserDao {
	private DataSource dataSource;

	public UserDaoImpl(DataSource dataSource) {
		this.dataSource = dataSource;

		String sql = "CREATE TABLE IF NOT EXISTS registration(rid SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, firstname VARCHAR(50), lastname VARCHAR(50), email VARCHAR(50), username VARCHAR(50), password VARCHAR(50), primary key(rid));";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
		jdbcTemplate.update(sql);
	}

	// public void setDataSource(DataSource dataSource) {
	// this.dataSource = dataSource;
	// }

	/*
	 * CREATE
	 */
	@Override
	public void insert(User user) {
		String sql = "INSERT INTO registration (firstname, lastname, email, username, password) SELECT ?,?,?,?,? FROM DUAL WHERE NOT EXISTS (SELECT username FROM registration WHERE username = ?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int out = jdbcTemplate.update(
				sql, 
				new Object[] {
					user.getFirstname(), 
					user.getLastname(), 
					user.getEmail(),
					user.getUsername(), 
					user.getPassword(), 
					user.getUsername()
				}
			);

		if (out != 0) {
			System.out.println("User inserted with name : " + user.getUsername());
		} else
			System.out.println("User inserted failed with name : " + user.getUsername());
	}

	/*
	 * READ
	 */
	@Override
	public User getById(long rid) {
		String sql = "SELECT * FROM registration WHERE rid = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		// // using RowMapper anonymous class, we can create a separate RowMapper for
		// reuse
		// User user = jdbcTemplate.queryForObject(sql, new Object[] { rid }, new
		// RowMapper<User>() {
		//
		// @Override
		// public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// User user = new User();
		//
		// user.setRid(rs.getInt("rid"));
		// user.setFirstname(rs.getString("firstname"));
		// user.setLastname(rs.getString("lastname"));
		// user.setEmail(rs.getString("email"));
		// user.setUsername(rs.getString("username"));
		// user.setPassword(rs.getString("password"));
		// return user;
		// }
		// });
		
		try {
			User user = jdbcTemplate.queryForObject(
					sql, 
					new Object[] { rid }, 
					new UserMapper()
				);
	
			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public User getByUsername(String username) {
		String sql = "SELECT * FROM registration WHERE username = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		try {
			User user = jdbcTemplate.queryForObject(
					sql, 
					new Object[] { username }, 
					new UserMapper()
				);
			
			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		String sql = "SELECT NULL AS rid, firstname, lastname, email, username, NULL AS password FROM registration WHERE username = ? AND password = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		try {
			User user = jdbcTemplate.queryForObject(
					sql, 
					new Object[] { username, password }, 
					new UserMapper()
				);
			
			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public User getByFullname(String firstname, String lastname) {
		String sql = "SELECT * FROM registration WHERE firstname = ? AND lastname = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		try {
			User user = jdbcTemplate.queryForObject(
					sql, 
					new Object[] { firstname, lastname }, 
					new UserMapper()
				);
	
			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public Integer countUsernameNumber(String username) {
		String sql = "SELECT COUNT(1) FROM registration WHERE username = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		try {
			Integer username_count = jdbcTemplate.queryForObject(
					sql, 
					new Object[] { username }, 
					new RowMapper<Integer>() {		
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							Integer username_count = rs.getInt("COUNT(1)");	
							return username_count;
						}
					}
				);
	
			return username_count;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public Boolean validateUserPassword(String username, String password) {
		String sql = "SELECT COUNT(1) FROM registration WHERE username = ? AND password =?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		try {
			Integer username_count = jdbcTemplate.queryForObject(
					sql, 
					new Object[] { username, password }, 
					new RowMapper<Integer>() {		
						@Override
						public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
							Integer username_count = rs.getInt("COUNT(1)");	
							return username_count;
						}
					}
				);
			
			if (username_count == 1)
				return true;
			else
				return false;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<User> getAll() {
		String sql = "SELECT * FROM registration";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		// List<User> userList = new ArrayList<User>();
		// List<Map<String, Object>> userRows = jdbcTemplate.queryForList(sql);
		//
		// for (Map<String, Object> userRow : userRows) {
		// User user = new User();
		//
		// user.setRid(Integer.parseInt(String.valueOf(userRow.get("rid"))));
		// user.setFirstname(String.valueOf(userRow.get("firstname")));
		// user.setLastname(String.valueOf(userRow.get("lastname")));
		// user.setEmail(String.valueOf(userRow.get("email")));
		// user.setUsername(String.valueOf(userRow.get("username")));
		// user.setPassword(String.valueOf(userRow.get("password")));
		//
		// userList.add(user);
		// }

		try {
			List<User> userList = jdbcTemplate.query(sql, new UserMapper());
	
			return userList;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/*
	 * UPDATE
	 */
	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateByUsername(String password, String username) {
		String sql = "UPDATE registration SET password=? WHERE username=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		//		Object[] args = new Object[] { password, username };

		int out = jdbcTemplate.update(
				sql, 
				new Object[] { password, username }
			);
		
		if (out != 0) {
			System.out.println("User updated with username: " + username);
		} else
			System.out.println("No User found with username: " + username);
	}

	
	/* 
	 * DELETE
	 */
	@Override
	public void delete(User user) {
		String sql = "DELETE FROM registration WHERE firstname=? AND lastname=? AND email=? AND username=? AND password=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		// Object[] args = new Object[] { user.getFirstname(), user.getLastname(), user.getEmail(), user.getUsername(), user.getPassword() };

		int out = jdbcTemplate.update(
				sql, 
				new Object[] { 
					user.getFirstname(), 
					user.getLastname(), 
					user.getEmail(), 
					user.getUsername(),
					user.getPassword()
				}
			);
		
		if (out != 0) {
			System.out.println("User deleted: " + user.getUsername());
		} else
			System.out.println("No User found: " + user.getUsername());
	}

	@Override
	public void deleteById(long id) {
		String sql = "DELETE FROM registration WHERE rid=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int out = jdbcTemplate.update(sql, id);
		if (out != 0) {
			System.out.println("User deleted with id: " + id);
		} else
			System.out.println("No User found with id: " + id);
	}



	

}
