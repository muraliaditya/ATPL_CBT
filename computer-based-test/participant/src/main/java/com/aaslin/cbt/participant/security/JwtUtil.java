package com.aaslin.cbt.participant.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

	    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

	    public static String generateToken(String participantId, String contestId) {
	        return Jwts.builder()
	                .setSubject(participantId)
	                .claim("contestId",contestId)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(SECRET_KEY)
	                .compact();
	    }

	    public static String validateTokenAndGetParticipantId(String token) {
	        return Jwts.parserBuilder()
	        		.setSigningKey(SECRET_KEY)
	        		.build()
	        		.parseClaimsJws(token)
	        		.getBody()
	        		.getSubject();
	   }
}
