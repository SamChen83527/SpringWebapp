package springwebapp;

import java.util.Date;

import idv.sam.springwebapp.authentication.JWTManager;
import io.jsonwebtoken.Claims;

public class JWTManagerTest {
	public static void main(String[] args) {
		JWTManager jwt_manager = new JWTManager();
		
		String jws = jwt_manager.createJWT("s83527", "s83527@gmail.com");
		System.out.println(jws);
		
		String SECRET_KEY_TEST = "veRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

		Claims claims;
		claims = JWTManager.decodeJWT(SECRET_KEY_TEST);
		if (claims != null) {
			System.out.println(claims);
			System.out.println(claims.toString());
			System.out.println(claims.getIssuer());		
			System.out.println(claims.getIssuedAt());
			System.out.println(claims.getExpiration());		
			System.out.println(claims.get("email"));
		} else {
			System.out.println("decode failed");
		}
	}		

}
