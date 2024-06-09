package com.spring_auth.spring_auth.util;


import com.spring_auth.spring_auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTUtil 
{

    @Value("${jwt.secret}")
    private String secretKey;
    public String extractEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {

        final Claims claims=extractAllClaims(jwtToken);
        return claimResolver.apply(claims);

    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSecretKey()).build().parseSignedClaims(jwtToken).getPayload();
    }

    public Key getSecretKey(){
        byte [] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String email=extractEmail(jwtToken);

        return email.equalsIgnoreCase(userDetails.getUsername()) && isTokenExpired(jwtToken);

    }

    private boolean isTokenExpired(String jwtToken) {

        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken,Claims::getExpiration);
    }

    public Object generateToken(User u) {

        return  Jwts.builder()
                .subject(u.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(getSecretKey())
                .compact();


    }
}
