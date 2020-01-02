package springwebapp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import idv.sam.springwebapp.dao.UserDao;
import idv.sam.springwebapp.dao.impl.UserDaoImpl;
import idv.sam.springwebapp.model.User;

public class UserMapperTest {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		UserDao userdaoimp = (UserDaoImpl) context.getBean("userDaoImpl");
		
		String username = "sea000s";
		String password = "YOLO";
		
		User user = userdaoimp.getUserByUsernameAndPassword(username, password);
		System.out.println("User name: " + user.getUsername());
		System.out.println("User password: " + user.getPassword());
	}
}
