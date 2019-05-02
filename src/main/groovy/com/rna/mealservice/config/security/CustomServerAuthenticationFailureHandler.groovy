package com.rna.mealservice.config.security

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.DefaultServerRedirectStrategy
import org.springframework.security.web.server.ServerRedirectStrategy
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import reactor.core.publisher.Mono

class CustomServerAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy()
    final URI uri

    CustomServerAuthenticationFailureHandler(String uri) {
        assert uri
        this.uri = URI.create(uri)
    }

    @Override
    Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        println "hello"
        webFilterExchange.exchange.response.statusCode = HttpStatus.FORBIDDEN
        println 'set to forbidden'
        return redirectStrategy.sendRedirect(webFilterExchange.exchange, uri)
    }
}
