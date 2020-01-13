package springwebapp;

import idv.sam.springwebapp.authentication.JWTManager;

public class JWTManagerTest {
	public static void main(String[] args) {
		JWTManager jwt_manager = new JWTManager();
		
		String jws = jwt_manager.createJWT("a123", "Login", "User");
		System.out.println(jws);
	}

}
