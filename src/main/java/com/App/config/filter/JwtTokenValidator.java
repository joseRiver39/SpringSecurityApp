/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.App.config.filter;

import com.App.util.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtTokenValidator extends OncePerRequestFilter {
    
    
    private JwtUtils JwtUtils;

    public JwtTokenValidator(JwtUtils JwtUtils) {
        this.JwtUtils = JwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
      
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (jwtToken != null) {
            
            
            jwtToken = jwtToken.substring(7);
            
            DecodedJWT decodedJWT = JwtUtils.validateToken(jwtToken);
            
            String username = JwtUtils.extractUsername(decodedJWT);
            
            String  stringAuthorities = JwtUtils.getEspecificClains(decodedJWT, "authorities").asString();       
              
            Collection<? extends  GrantedAuthority> authorities =  AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);
           
            SecurityContext contex = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);
            contex.setAuthentication(authentication);
            SecurityContextHolder.setContext(contex);
        }
        
        filterChain.doFilter(request, response);
    }

  
    
}
