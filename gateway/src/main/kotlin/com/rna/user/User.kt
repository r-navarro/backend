package com.rna.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.rna.security.PasswordEncoder
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Introspected
data class User(
        @field:NotEmpty(message = "A user need a name")
        var name: String = "",
        @field:NotEmpty(message = "A user need an email")
        @field:NotNull(message = "A user need an email")
        @field:Email(message = "Not an email")
        var email: String = "",
        @field:NotEmpty(message = "A user need a password")
        @field:NotNull(message = "A user need a password")
        var password: String = "",
        var roles: List<String> = emptyList()
) {
    fun getEncryptedUser(passwordEncoder: PasswordEncoder) = User(name, email, passwordEncoder.encode(password), roles)

    override fun toString(): String = ObjectMapper().writeValueAsString(this)
}
