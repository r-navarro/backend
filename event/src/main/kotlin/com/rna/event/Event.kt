package com.rna.event

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Introspected
data class Event(
        @field:NotEmpty(message = "An event need a type")
        @field:NotNull(message = "An event need a type")
        var type: String = "",
        @field:NotEmpty(message = "An event need a sender")
        @field:NotNull(message = "An event need a sender")
        var sender: String = "",
        var timestamp: Long = 0) {
}

data class ApiError(val message: String?)
