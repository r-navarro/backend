package com.rna.mealservice.config.security

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

import javax.naming.AuthenticationException

@Component
class CustomAuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    Mono<Authentication> authenticate(Authentication authentication)
            throws AuthenticationException {

        def name = authentication.getName()
        def password = authentication.getCredentials().toString()

        if (name == "admin" && password == "admin") {
            return Mono.just(new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>()))
        }
        Mono.error(new BadCredentialsException("Invalid Credentials"))
    }
}
