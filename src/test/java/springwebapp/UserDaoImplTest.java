package springwebapp;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import idv.sam.springwebapp.dao.UserDao;
import idv.sam.springwebapp.dao.impl.UserDaoImpl;
import idv.sam.springwebapp.model.User;

public class UserDaoImplTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");

		UserDao userdaoimp = (UserDaoImpl) context.getBean("userDaoImpl");
		
		String firstname = "Hsuan";
	    String lastname = "Lee";
	    String email = "shelley@hotmail.com";
	    String username = "shelley";
	    String password = "PHD.Li";
	    
	    // INSERT DATA
		User user1 = new User("Sam","Chen","s83527@gmail.com","s83527","88888888");
		userdaoimp.insert(user1);
		
		userdaoimp.insert(new User("Doreen","Chien","doreen@gmail.com","Doreen","DCH"));
		userdaoimp.insert(new User("Jing","Wu","sea000s@hotmail.com","sea000s","YOLO"));
		userdaoimp.insert(new User(firstname,lastname,email,username,password));
		
		// READ DATA BY ID		
		User user2 = userdaoimp.getById(1);
		System.out.println(user2.getFirstname() + " " + user2.getLastname() + ": " + user2.getEmail() +" / " + user2.getUsername() +" / "+ user2.getPassword());
		User user3 = userdaoimp.getById(2);
		System.out.println(user3.getFirstname() + " " + user3.getLastname() + ": " + user3.getEmail() +" / " + user3.getUsername() +" / "+ user3.getPassword());
	
		// READ DATA BY USERNAME
		User user4 = userdaoimp.getByUsername("sea0c00s");
		if (user4 == null)
			System.out.println("NULL");
		else
			System.out.println("getByUsername: " + user4.getFirstname() + " " + user4.getLastname() + ": " + user4.getEmail() +" / " + user4.getUsername() +" / "+ user4.getPassword());
		
			
	
		// READ DATA BY FULLNAME
		User user5 = userdaoimp.getByFullname("Sam", "Chen");
		System.out.println("getByFullname: " + user5.getFirstname() + " " + user5.getLastname() + ": " + user5.getEmail() +" / " + user5.getUsername() +" / "+ user5.getPassword());
		
		// READ ALL DATA
		List<User> users = userdaoimp.getAll();System.out.println("");
		for (User u:users) {
			System.out.println("getAll: " + u.getFirstname() + " " + u.getLastname() + ": " + u.getEmail() +" / " + u.getUsername() +" / "+ u.getPassword());
		}
		
		// UPDATE DATA BY USERNAME
		String update_username = "shelley";
		String update_password = "NOISyyyyyy";
		userdaoimp.updateByUsername(update_password, update_username);
		User user6 = userdaoimp.getByUsername("shelley");
		System.out.println("getByFullname: " + user6.getFirstname() + " " + user6.getLastname() + ": " + user6.getEmail() +" / " + user6.getUsername() +" / "+ user6.getPassword());
		
		// DELETE DATA BY BEAN
		User user7 = new User("Huanci","Ho","henry@gmail.com","henrysuck","ugly");
		userdaoimp.insert(user7);		
		User user8 = userdaoimp.getByUsername("henrysuck");
		System.out.println("getByUsername: " + user8.getFirstname() + " " + user8.getLastname() + ": " + user8.getEmail() +" / " + user8.getUsername() +" / "+ user8.getPassword());
		userdaoimp.delete(user8);
		
		// DELETE DATA BY ID
		User user9 = new User("Huanci","Hohoho","henry@gmail.com","henryHello","ugly");
		userdaoimp.insert(user9);
		User user10 = userdaoimp.getByUsername("henryHello");
		long rid = user10.getRid();
		userdaoimp.deleteById(rid);
		userdaoimp.getByUsername("henryHello");
		
		// getUsernameIfExists
		System.out.println(userdaoimp.countUsernameNumber("fff"));
		// validateUserPassword
		System.out.println(userdaoimp.validateUserPassword("stupidhenry","00000000"));
		
		//close the context
		((AbstractApplicationContext) context).close();
	}

}
