package com.rna.mealservice.config


import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@ComponentScan("com.rna.mealservice")
@EnableReactiveMongoRepositories("com.rna.mealservice.repositories")
@EnableMongoAuditing
@Order(Ordered.HIGHEST_PRECEDENCE)
class ApplicationConfig {


}
