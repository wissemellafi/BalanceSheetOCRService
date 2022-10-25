package com.amenbank.bilan_ocr.filter;

import com.amenbank.bilan_ocr.dto.auth.AuthenticatedUser;
import com.amenbank.bilan_ocr.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class UsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public UsernamePasswordAuthFilter(String signingKey, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwtUtil = new JwtUtil(signingKey);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && password != null) {
            var auth = new UsernamePasswordAuthenticationToken(
                    username,
                    password
            );

            auth.setDetails(this.authenticationDetailsSource.buildDetails(request));
            return getAuthenticationManager().authenticate(auth);
        } else {
            throw new AuthenticationCredentialsNotFoundException("Username and password are required to authenticate");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        var username = authResult.getName();
        var authorities = authResult.getAuthorities();
        var role =
                authorities.stream().findFirst().isPresent() ?
                authorities.stream().findFirst().get().getAuthority() : "";

        var expiresIn = 24 * 60 * 60; // Number of seconds until the token expires
        var expirationDate = new Date(System.currentTimeMillis() + expiresIn * 1000);
        var token = jwtUtil.generateToken(
                username,
                Map.of(
                        "role",
                        role
                ),
                request.getRequestURI(),
                expirationDate
        );

        var authUser = new AuthenticatedUser(
                username,
                role,
                token,
                expiresIn
        );

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), authUser);
    }
}
