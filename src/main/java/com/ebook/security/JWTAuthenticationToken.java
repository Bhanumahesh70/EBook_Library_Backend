package com.ebook.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JWTAuthenticationToken extends AbstractAuthenticationToken{

    private final String username;
    private final Claims claims;

    public JWTAuthenticationToken( String username, Claims claims) {
        super(List.of(new SimpleGrantedAuthority(claims.get("role",String.class))));
        this.username = username;
        this.claims = claims;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    public Claims getClaims(){
        return  claims;
    }
}
