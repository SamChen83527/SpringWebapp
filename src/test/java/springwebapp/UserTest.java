package springwebapp;

import idv.sam.springwebapp.model.User;

public class UserTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String firstname = "Sam";
	    String lastname = "Chen";
	    String email = "sea000s@hotmail.com";
	    String username = "s83527";
	    String password = "funny";
	    
		User user1 = new User(firstname,lastname,email,username,password);		
		System.out.println("User1 string: " + user1.toString());
		
		User user2 = new User(user1.toString());
		System.out.println("User2 string: " + user2.toString());
		
		User user3 = new User(
				user1.getFirstname(),
				user1.getLastname(),
				user1.getEmail(),
				user1.getUsername(),
				user1.getPassword());	
		System.out.println("User3 string: " + user3.toString());
		
		User user4 = new User();
		user4.setFirstname("Bruno");
		user4.setLastname("Mars");
		user4.setEmail("Bruno001@gmail.com");
		user4.setUsername("brunoMonkey");
		user4.setPassword("lazyallday");				
		System.out.println("User4 string: " + user4.toString());
	}
	
}
