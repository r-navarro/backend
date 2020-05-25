package com.rna.security

import com.rna.user.UsersService
import io.micronaut.security.authentication.*
import io.micronaut.security.authentication.providers.PasswordEncoder
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import javax.inject.Singleton

@Singleton
class GatewayAuthenticationProvider(private val usersService: UsersService, private val passwordEncoder: PasswordEncoder) : AuthenticationProvider {
    override fun authenticate(authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse> {
        return usersService.search(authenticationRequest.identity as String)
                .flatMapPublisher { user ->
                    if (passwordEncoder.matches(authenticationRequest.secret as String, user.password)) {
                        return@flatMapPublisher Flowable.just(UserDetails(user.name, user.roles))
                    }
                    Flowable.just(AuthenticationFailed())
                }
    }
}
