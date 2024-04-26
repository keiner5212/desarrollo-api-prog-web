package eshop.prod.database.entities.segurity;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import io.jsonwebtoken.Claims;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import eshop.prod.utils.constants.Security;

@Component
@Slf4j
public class JWTGenerator {
	private static final SecretKey key = Jwts.SIG.HS512.key().build();
	
	public String generateToken(Authentication authentication) {
        
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + Security.JWT_EXPIRATION);
		
		String token = Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(expireDate)
				.signWith(key)
				.compact();
                
        log.info("Token generated: " + token);
		return token;
	}

	public String getUsernameFromJWT(String token){
		Claims claims = Jwts.parser()
                .verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
            .verifyWith(key)
			.build()
			.parseSignedClaims(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
		}
	}

}