package com.rna.room

import com.rna.shutter.MessageDTO
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
import org.slf4j.LoggerFactory

@Controller("/rooms")
@Secured(SecurityRule.IS_AUTHENTICATED)
class RoomsController(private val roomClient: RoomClient) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val log = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    @Get("/{id}")
    fun getRoomData(@PathVariable id: Int): Single<HttpResponse<*>> {
        log.debug("calling rooms $id")
        return roomClient.getRoomsData(id)
    }

    @Get("/")
    fun getRoomsData(): Single<HttpResponse<*>> {
        log.debug("calling rooms")
        return roomClient.getAllRoomsData()
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
