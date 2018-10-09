package com.rna.mealservice.config

import com.rna.mealservice.config.security.CustomAuthenticationProvider
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component

@Component
@Primary
@Profile('test')
class CustomMockAuthenticationProvider extends CustomAuthenticationProvider {
    @Override
    Authentication authenticate(Authentication authentication) throws AuthenticationException {
        new UsernamePasswordAuthenticationToken('name', 'password', new ArrayList<>())
    }

    @Override
    boolean supports(Class<?> authentication) {
        authentication == UsernamePasswordAuthenticationToken
    }
}
