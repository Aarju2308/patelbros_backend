	package com.patelbros.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final long JWT_EXPIRATION = 8640000 ; // 15 minutes in milliseconds
	private final String SECRET_KEY = "fd1493657356d94b7e85a3c9a3a4676b0deadf5b17c55ffea3cfadd1310eee5d";
	
	public String createToken(UserDetails details) {
		return createToken(new HashMap<>(),details);
	}

	public String createToken(Map<String, Object> claims, UserDetails details) {
		
		var authorities = details.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(details.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
				.claim("authorities", authorities)
				.signWith(getSignInKey())
				.compact();
	}

	private Key getSignInKey() {
		byte[]  bs = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(bs);
	}
	
	public String getUserEmail(String jwt) {
		return extractClaims(jwt, Claims::getSubject);
	}

	private <T> T extractClaims(String jwt, Function<Claims,T> claimResolver) {
		Claims claims = extractAllClaims(jwt);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String jwt) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(jwt)
				.getBody();
	}

	public boolean isTokenValid(String jwt, UserDetails details) {
		final String userName = getUserEmail(jwt);
		return (userName.equals(details.getUsername()) && !isTokenExpired(jwt));
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date()) ;
	}


	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}

}
