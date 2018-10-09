package com.rna.mealservice.config.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

import javax.naming.AuthenticationException

@Component
class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        def name = authentication.getName()
        def password = authentication.getCredentials().toString()

        if (name == "admin" && password == "password") {
            return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>())
        }
        null
    }

    @Override
    boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken
    }
}
