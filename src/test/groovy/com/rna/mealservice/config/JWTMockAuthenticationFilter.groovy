package com.rna.mealservice.config

import com.rna.mealservice.config.security.JWTAuthenticationFilter
import com.rna.mealservice.config.security.TokenAuthenticationService
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

import javax.security.auth.Subject
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import java.security.Principal

@Component
@Primary
@Profile('test')
class JWTMockAuthenticationFilter extends JWTAuthenticationFilter {

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        def authentication = [
                getAuthorities  : {
                    return null
                },
                getCredentials  : {
                    return null
                },
                getDetails      : {
                    return null
                },
                getPrincipal    : {
                    new TestingAuthenticationToken(new User('test', 'password', []), null)
                },
                isAuthenticated : {
                    return true
                },
                setAuthenticated: { boolean isAuthenticated ->

                },
                getName         : {
                    return null
                },
                implies         : { Subject subject -> }
        ] as Authentication
        SecurityContextHolder.getContext().setAuthentication(authentication)
        filterChain.doFilter(request, response)
    }
}
