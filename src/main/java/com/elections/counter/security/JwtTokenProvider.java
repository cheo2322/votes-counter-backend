package com.elections.counter.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  @Value("${app.jwt-secret}")
  private String jwtSecret;

  @Value("${app-jwt-expiration-milliseconds}")
  private long jwtExpirationDate;

  public String generateToken(Authentication authentication) {
    Date currentDate = new Date();
    Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

    return Jwts.builder()
        .setSubject(authentication.getName())
        .setIssuedAt(new Date())
        .setExpiration(expireDate)
        .signWith(getKey())
        .compact();
  }

  private Key getKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String getUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateToken(String token){
    try{
      Jwts.parserBuilder()
          .setSigningKey(getKey())
          .build()
          .parse(token);
      return true;
    } catch (Exception e){
      throw new RuntimeException("");
    }
  }
}
