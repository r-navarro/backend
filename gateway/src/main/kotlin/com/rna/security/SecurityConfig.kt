package com.rna.security

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.security.authentication.providers.PasswordEncoder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.inject.Singleton

@Factory
class SecurityConfig {

    @Bean
    @Singleton
    fun passwordEncoder(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(rawPassword: String): String {
                val messageDigest = MessageDigest.getInstance("SHA-256")
                val digest = messageDigest.digest(rawPassword.toByteArray(StandardCharsets.UTF_8))
                return String(digest)
            }

            override fun matches(rawPassword: String, encodedPassword: String): Boolean {
                return encode(rawPassword) == encodedPassword
            }
        }
    }
}
