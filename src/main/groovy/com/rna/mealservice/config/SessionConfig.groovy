package com.rna.mealservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.data.mongo.JdkMongoSessionConverter
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession

import java.time.Duration

@Configuration
@EnableMongoHttpSession
class SessionConfig {

    @Bean
    JdkMongoSessionConverter jdkMongoSessionConverter() {
        new JdkMongoSessionConverter(Duration.ofMinutes(30))
    }
}
