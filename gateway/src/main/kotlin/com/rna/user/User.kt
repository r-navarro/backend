package com.rna.user

import io.micronaut.core.annotation.Introspected
import io.micronaut.security.authentication.providers.PasswordEncoder
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Introspected
data class User(
        @field:NotEmpty(message = "A user need a name")
        var name: String = "",
        @field:NotEmpty(message = "A user need an email")
        @field:NotNull(message = "A user need an email")
        var email: String = "",
        @field:NotEmpty(message = "A user need a password")
        @field:NotNull(message = "A user need a password")
        var password: String = "",
        var roles: List<String> = emptyList()
) {
    fun getEncryptedUser(passwordEncoder: PasswordEncoder) = User(name, email, passwordEncoder.encode(password), roles)
}
