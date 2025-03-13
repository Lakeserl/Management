package com.StudentManagements.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.StudentManagements.models.Student;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class JwtProvider {

    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication, Student student) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populatesAuthorities(authorities);

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 840000000))
                .claim("email", student.getEmail())
                .claim("studentId", student.getStudentId())
                .claim("fullName", student.getFullName())
                .claim("phone", student.getPhone())
                .claim("dob", student.getDob() != null ? student.getDob().getTime() : null)
                .claim("gender", student.getGender())
                .claim("address", student.getAddress())
                .claim("role", student.getRole().toString())
                .claim("authorities", roles)
                .signWith(key)
                .compact();

        return jwt;
    }

    public Claims extractClaims(String jwt) {
        jwt = jwt.substring(7);
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
    }

    public String getEmailFromJwtToken(String jwt) {
        return extractClaims(jwt).get("email", String.class);
    }

    public String getStudentIdFromJwtToken(String jwt) {
        return extractClaims(jwt).get("studentId", String.class);
    }

    public String getFullNameFromJwtToken(String jwt) {
        return extractClaims(jwt).get("fullName", String.class);
    }

    public String getPhoneFromJwtToken(String jwt) {
        return extractClaims(jwt).get("phone", String.class);
    }

    public Date getDobFromJwtToken(String jwt) {
        Long timestamp = extractClaims(jwt).get("dob", Long.class);
        return timestamp != null ? new Date(timestamp) : null;
    }

    public int getGenderFromJwtToken(String jwt) {
        return extractClaims(jwt).get("gender", Integer.class);
    }

    public String getAddressFromJwtToken(String jwt) {
        return extractClaims(jwt).get("address", String.class);
    }

    public String getRoleFromJwtToken(String jwt) {
        return extractClaims(jwt).get("role", String.class);
    }

    public String getAuthoritiesFromJwtToken(String jwt) {
        return extractClaims(jwt).get("authorities", String.class);
    }

    public String populatesAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }
}
