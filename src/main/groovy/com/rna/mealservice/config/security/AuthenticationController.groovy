package com.rna.mealservice.config.security

import com.rna.mealservice.config.security.dto.AccountCredentials
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono

@RestController
class AuthenticationController {

    @PostMapping('/login')
    Mono<ResponseEntity> login(WebSession session, @RequestBody AccountCredentials credentials){
        if (credentials.userName == "admin" && credentials.password == "admin") {
            def auth = new UsernamePasswordAuthenticationToken(credentials.userName,
                    credentials.password,
                    Collections.emptyList()
            )
            session.attributes.putIfAbsent(SecurityContextRepository.SPRING__SECURITY__CONTEXT, new SecurityContextImpl(authentication: auth))
            return Mono.just(ResponseEntity.ok().build())
        }
        return  Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build())
    }
}
