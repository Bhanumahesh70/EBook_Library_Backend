package com.ebook.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthenticationToken extends AbstractAuthenticationToken{

    private final String username;
    private final Claims claims;

    public JWTAuthenticationToken( String username, Claims claims) {
        super(null);
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
