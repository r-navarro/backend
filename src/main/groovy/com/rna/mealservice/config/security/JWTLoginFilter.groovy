package com.rna.mealservice.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.rna.mealservice.config.security.dto.AccountCredentials
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url))
        setAuthenticationManager(authManager)
    }

    @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        AccountCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials)
        def usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(credentials.userName,
                credentials.password,
                Collections.emptyList()
        )
        getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken)
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        TokenAuthenticationService.addAuthentication(request, auth.getName())
    }
}
