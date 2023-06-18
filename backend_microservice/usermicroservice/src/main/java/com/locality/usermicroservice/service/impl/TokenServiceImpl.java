package com.locality.usermicroservice.service.impl;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.locality.usermicroservice.exception.ExpiredTokenException;
import com.locality.usermicroservice.payload.UserAuthDto;
import com.locality.usermicroservice.payload.UserDto;
import com.locality.usermicroservice.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {
	
	private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private final long EXPIRATION_TIME = 86400000;
	
	@Override
	public String generateToken(UserDto user) {
		

        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        
        log.info("Generating token");

        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .claim("username", user.getUsername())
                .claim("role", user.getRole().toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SECRET_KEY)
				.compact();
	}

	@Override
	public Boolean validateToken(String token) {
		

		try {
			Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
			
			log.info("Token validation success");
			return true;
		} catch (Exception e) {
			log.error("Token validation failed");
			
			throw new ExpiredTokenException("Invalid token");
		}
	}
	
	@Override
	public UserAuthDto validateUser(String token) {
		
		this.validateToken(token);
		
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		log.info("Token validated. Generating user");
		return UserAuthDto.builder()
				.userId(Long.parseLong(claims.getSubject()))
				.username(claims.get("username", String.class))
				.role(claims.get("role", String.class))
				.build();
	}

}
