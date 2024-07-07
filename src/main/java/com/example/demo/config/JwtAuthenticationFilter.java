package com.example.demo.config;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//
//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if ("/openApi/account/refresh".equals(request.getRequestURI())) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String authToken = authHeader.substring(7);
//            try {
//                Claims claims = Jwts.parser()
//                        .setSigningKey(Constant.SECRET_KEY)
//                        .parseClaimsJws(authToken)
//                        .getBody();
//                
//                String id = claims.getSubject();
//
//                CryptoUtil cryptoUtil = new CryptoUtil();
//                if (id != null && claims.get("d") != null
//                        && request.getHeader("accountId") != null) {
//                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(id, null, new ArrayList<>());
//                    SecurityContextHolder.getContext().setAuthentication(auth);
//                    String d = cryptoUtil.AESDecrypt((String) claims.get("d"));
//                    String accountId = d.split("_")[1];
//                    if (!accountId.equals(request.getHeader("accountId"))) {
//                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
//                    }
//                }
//            } catch (Exception e) {
//                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token", e);
//            }
//        }
        chain.doFilter(request, response);
    }
}