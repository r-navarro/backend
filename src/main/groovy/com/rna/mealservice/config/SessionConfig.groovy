package com.rna.mealservice.config


import org.springframework.context.annotation.Configuration
import org.springframework.session.data.mongo.config.annotation.web.reactive.EnableMongoWebSession

@Configuration
@EnableMongoWebSession
class SessionConfig {

}
