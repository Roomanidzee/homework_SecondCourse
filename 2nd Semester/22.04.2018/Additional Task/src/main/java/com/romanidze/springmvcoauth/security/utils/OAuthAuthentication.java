package com.romanidze.springmvcoauth.security.utils;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 28.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class OAuthAuthentication extends AbstractAuthenticationToken{

    private String user;
    private String token;

    public OAuthAuthentication(String user, String token){
        super(null);
        this.user = user;
        this.token = token;
    }

    public OAuthAuthentication(String user, String token, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.user = user;
        this.token = token;
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
       return null;
    }

    public void setUser(String user){
        this.user = user;
    }

    public String getUser(){
        return this.user;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}
