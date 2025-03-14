package com.ebook.security;
import io.jsonwebtoken.Jwts;
import com.ebook.domain.User;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JWTService {

    //private static final String SECRET_KEY = "A@456789$#rtyuiop";
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long Expiration_time = 86400000;

   // private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    public String generateToken(User user){

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+Expiration_time))
                .signWith(key)
                .compact();



    }
}
