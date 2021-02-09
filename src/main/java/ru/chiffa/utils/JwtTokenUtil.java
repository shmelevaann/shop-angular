package ru.chiffa.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        Date issuedDate = new Date();
        return Jwts.builder()
                .setClaims(getClaimsFromUserDetails(userDetails))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(new Date(issuedDate.getTime() + 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private Map<String, Object> getClaimsFromUserDetails(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);
        return claims;
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
