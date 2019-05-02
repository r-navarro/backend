package com.rna.mealservice.config

import com.rna.mealservice.config.security.CustomAuthenticationManager
import com.rna.mealservice.config.security.SecurityContextRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.logout.HttpStatusReturningServerLogoutSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@ComponentScan('com.rna')
class SecurityConfig implements WebFluxConfigurer {

    @Autowired
    CustomAuthenticationManager customAuthenticationManager

    @Autowired
    SecurityContextRepository securityContextRepository

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf().disable()
        http.cors()
        http.formLogin().disable()
        http.httpBasic()
        http.authenticationManager(customAuthenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/login").permitAll()
                .pathMatchers(HttpMethod.OPTIONS, "/login").permitAll()
                .pathMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                .anyExchange().authenticated()

        http.logout().logoutUrl("/logout")
                .and()
                .logout().logoutSuccessHandler((new HttpStatusReturningServerLogoutSuccessHandler(HttpStatus.OK)))

        http.build()
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration()
        configuration.allowedOrigins = ['*']
        configuration.allowedMethods = ['*']
        configuration.allowedHeaders = ['*']
        configuration.allowCredentials = true
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        source
    }
}
