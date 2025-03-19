package com.ebook.security;
import io.jsonwebtoken.*;
import com.ebook.domain.User;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JWTService {

//    private static final String SECRET_KEY = "A@456789$#rtyuiop";
//    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

//    private static final String SECRET_KEY = "A@456789$#rtyuiopA@456789$#rtyuiop"; // 256-bit key
//    private static final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long Expiration_time = 86400000;
    private static final JwtParser jwtParser = Jwts.parser().verifyWith((SecretKey) key).build();

    public String generateToken(User user){

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+Expiration_time))
                .signWith(key)
                .compact();

    }

    public Claims validateToken(String token) {
        try {
          Jws< Claims> claimsJws =  jwtParser.parseSignedClaims(token);
          return claimsJws.getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired token: " + e.getMessage());
        }
    }
}
