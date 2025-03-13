package com.StudentManagements.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.StudentManagements.models.Student;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String jwtToken = request.getHeader(JwtConstant.JWT_HEADER);

        if(jwtToken != null) {
            jwtToken = jwtToken.substring(7);

            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();

                String email = String.valueOf(claims.get("email"));
                String studentId = String.valueOf(claims.get("studentId"));
                String fullName = String.valueOf(claims.get("fullName"));
                String phone = String.valueOf(claims.get("phone"));
                String dob = String.valueOf(claims.get("dob"));
                Integer gender = Integer.valueOf(String.valueOf(claims.get("gender")));
                String authorities = String.valueOf(claims.get("authorities"));
                
                Student studentDetail = new Student(email,id,student);

                List<GrantedAuthority> auth= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication authentication = new UsernamePasswordAuthenticationToken(studentDetail,null,auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch (Exception e) {
                throw new BadCredentialsException("Invalid JWT token....");
            }
        }

        filterChain.doFilter(request, response);
    }
}
