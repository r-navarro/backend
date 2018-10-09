package com.rna.mealservice.config

import com.rna.mealservice.config.security.JWTAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@ComponentScan("com.rna.mealservice")
@EnableMongoRepositories("com.rna.mealservice.repositories")
@EnableMongoAuditing
@Order(Ordered.HIGHEST_PRECEDENCE)
class ApplicationConfig {

    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter()
    }

}
