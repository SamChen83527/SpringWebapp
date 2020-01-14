package idv.sam.springwebapp.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class JWTManager {
	final Base64.Encoder encoder = Base64.getEncoder();

	// The secret key. This should be in a property file NOT under source
	// control and not hard coded in real life. We're putting it here for
	// simplicity.
	private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";
	
	public String createJWT(String username, String email) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		
		
	    //Hours to milliseconds
		int hours = 1;
        long ttlMillis = hours * 3600000;
        long nowMillis = System.currentTimeMillis();
	    long expMillis = nowMillis + ttlMillis;	    
	    Date now = new Date(nowMillis);
	    Date exp = new Date(expMillis);
		
		/*
		 * iss (Issuer) - jwt簽發者
		 * sub (Subject) - jwt所面向的用戶
		 * aud (Audience) - 接收jwt的一方
		 * exp (Expiration Time) - jwt的過期時間，這個過期時間必須要大於簽發時間
		 * nbf (Not Before) - 定義在什麼時間之前，該jwt都是不可用的
		 * iat (Issued At) - jwt的簽發時間
		 * jti (JWT ID) - jwt的唯一身份標識，主要用來作為一次性token,從而迴避重放攻擊
		 */
		
		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder()
				.setIssuer("SENZOR Corp.")
				.setAudience(username)
				.setIssuedAt(now)
				.setExpiration(exp)
				.claim("email", email)
				.signWith(signingKey);

		return builder.compact();
	}

	public static Claims decodeJWT(String jwt) {
		// This line will throw an exception if it is not a signed JWS (as expected)
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
					.parseClaimsJws(jwt)
					.getBody();
			return claims;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}		
	}
}
