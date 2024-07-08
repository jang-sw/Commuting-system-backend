package com.example.demo.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.service.UserService;
import com.example.demo.util.CryptoUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	
	
	
	private final UserService userService;
	
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if ("/openApi/account/refresh".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(Constant.SECRET_KEY)
                        .parseClaimsJws(authToken)
                        .getBody();
                
                String id = claims.getSubject();

                CryptoUtil cryptoUtil = new CryptoUtil();
                if (id != null && claims.get("d") != null
                        && request.getHeader("accountId") != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    String d = cryptoUtil.AESDecrypt((String) claims.get("d"));
                    String accountId = d.split("_")[1];
                    if (!accountId.equals(request.getHeader("accountId"))) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
                    }
                    if (request.getRequestURI().startsWith("/adminApi")) {
                    	String auth = userService.getUser(Long.parseLong(accountId)).getAuth();
                    	if (!auth.equals("ADMIN")) {
                    		response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
                        	return;
                        }
                    }
                }
            } catch (Exception e) { 
            	response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
            	return;
            }
        }
        chain.doFilter(request, response);
    }
}