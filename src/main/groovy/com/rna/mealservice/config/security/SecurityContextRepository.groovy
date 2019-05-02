package com.rna.mealservice.config.security


import org.springframework.security.core.context.SecurityContext
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository extends WebSessionServerSecurityContextRepository {


    public static final String SPRING__SECURITY__CONTEXT = "SPRING_SECURITY_CONTEXT"

    Mono<SecurityContext> load(ServerWebExchange exchange) {
        return exchange.session
                .map { it.attributes }
                .flatMap { attrs ->
                    def context = attrs.get(SPRING__SECURITY__CONTEXT) as SecurityContext
                    return Mono.justOrEmpty(context);
                }
    }

}
