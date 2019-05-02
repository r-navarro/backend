package com.rna.mealservice.config

import com.rna.mealservice.config.security.CustomAuthenticationManager
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
@Primary
@Profile('test')
class CustomMockAuthenticationManager extends CustomAuthenticationManager {
    @Override
    Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        Mono.just(new UsernamePasswordAuthenticationToken('name', 'password', new ArrayList<>()))
    }

}
