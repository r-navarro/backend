package com.rna.shutter

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.AuthorizationException
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Single

@Controller("/rooms")
@Secured(SecurityRule.IS_AUTHENTICATED)
class RoomsController(private val roomClient: RoomClient) {

    @Get("/{id}")
    fun open(@PathVariable id: Int): Single<HttpResponse<*>> {
        return roomClient.getData(id)
    }

    @Error
    fun errorHandler(e: Throwable): HttpResponse<MessageDTO> {
        return when (e) {
            is HttpClientResponseException -> HttpResponse.badRequest(MessageDTO(e.status.code, e.localizedMessage))
            is AuthorizationException -> HttpResponse.unauthorized()
            else -> HttpResponse.badRequest(MessageDTO(0, e.localizedMessage))
        }
    }
}
