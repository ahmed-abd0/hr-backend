package com.hr.api.security.util;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;

@Component
@Log
public class JwtUtil {

    @Value("${spring.jwt.secret}")
    private  String secret;

    @Value("${spring.jwt.expiration}")
    private  long expiration;

   
	public String getJwtFromHeader(HttpServletRequest request) {
		
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		log.info("JWT Bearer Tokent : " + token);
		
		if(token != null && token.startsWith("Bearer ")) {
			return token.substring(7);
		}
		
		return null;
	}


    public String generateToken(String email, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(email)
                .issuedAt(this.date())
                .expiration(this.date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateToken(String username) {
        return generateToken(username, Map.of());
    }

    public boolean isTokenValid(String token, String expectedEmail) {
        try {
            String email = extractEmail(token);
            return email.equals(expectedEmail) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }
    

    private Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build();

        return parser.parseSignedClaims(token).getPayload();
    }

	
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes); // HS256
    }
	
	private Date date() {
	
		return new Date();
	}
	
	private Date date(long date) {
		
		return new Date(date);
	}
	
	
	
}
