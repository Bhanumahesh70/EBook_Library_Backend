package com.ebook.security;

import com.ebook.service.PublisherService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(JWTAuthenticationFilter.class.getName());
    private final JWTService jwtService;

    @Autowired
    public JWTAuthenticationFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            logger.warning("No JWT token");
            return;
        }
        String token = authorizationHeader.substring(7);
        try{
            Claims claims = jwtService.validateToken(token);
            logger.info("JWT is validated. User: "+ claims.getSubject());
            SecurityContextHolder.getContext().setAuthentication(new JWTAuthenticationToken(claims.getSubject(),claims));
        }catch (JwtException e){
            logger.warning("Invalid JWT token. Error: "+ e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid JWT token");
            return;
        }

        filterChain.doFilter(request,response);
    }
}
