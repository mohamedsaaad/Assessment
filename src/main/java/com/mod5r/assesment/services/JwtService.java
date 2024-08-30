package com.mod5r.assesment.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.function.Function;

public interface JwtService {
    String extractUserName(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    String generateToken(HashMap<String, Object> extraClaims, UserDetails userDetails);

    long getExpirationTime();

    boolean isTokenValid(String token, UserDetails userDetails);
}
