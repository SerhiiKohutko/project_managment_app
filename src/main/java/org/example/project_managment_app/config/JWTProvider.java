package org.example.project_managment_app.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JWTConstant.JWT_SECRET.getBytes());
    public static String createJWT(Authentication authentication) {

        return Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+8640000))
                .claim("email", authentication.getName())
                .signWith(key)
                .compact();
    }

    public static String getEmailFromJWT(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(key).build().parseSignedClaims(jwt).getPayload();

        return String.valueOf(claims.get("email"));
    }
}
