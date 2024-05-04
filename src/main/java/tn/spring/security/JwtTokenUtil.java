package tn.spring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Generate a secure random key

    // This method generates a JWT token based on the user's email
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .signWith(secretKey) // Sign the token with the secure random key
                .compact();
    }

    // This method validates the JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // This method extracts the email from the JWT token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // This method extracts a claim from the JWT token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // This method extracts all claims from the JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}