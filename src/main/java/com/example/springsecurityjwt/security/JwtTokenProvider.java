package com.example.springsecurityjwt.security;


import com.example.springsecurityjwt.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecretKey;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(UserPrincipal userPrincipal){

        // User roles
        Set<Role> roles = userPrincipal.getRoles();

        // Setting claims
        Claims claims = Jwts.claims().setSubject(userPrincipal.getId().toString());
        claims.put("roles",roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet()));

        // Expiration Date
        Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()))
                .compact();
    }

    public String getUserIdFromToken(String token){
        Claims body = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecretKey.getBytes())).build().parseClaimsJws(token).getBody();
        return  body.getSubject();
    }

    public String getRolesFromToken(String token){
        Claims body = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecretKey.getBytes())).build().parseClaimsJws(token).getBody();
        return "";
    }

    public boolean validateToken(String token) {
        boolean isTokenVerified = false;
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecretKey.getBytes())).build().parseClaimsJws(token);
            isTokenVerified = true;
        } catch (MalformedJwtException ex) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new IllegalStateException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("JWT claims string is empty.");
        }finally {
            return isTokenVerified;
        }
    }
}
