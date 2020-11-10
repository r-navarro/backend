package com.rna.shutter

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.AuthorizationException
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Single
import org.slf4j.LoggerFactory

@Controller("/shutters")
@Secured(SecurityRule.IS_AUTHENTICATED)
class ShuttersController(private val shutterClient: ShutterClient) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val log = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    @Post("/open/{id}")
    fun open(@PathVariable id: Int): Single<HttpResponse<*>> {
        log.debug("calling open shutter $id")
        return shutterClient.open(id)
    }

    @Post("/close/{id}")
    fun close(@PathVariable id: Int): Single<HttpResponse<*>> {
        log.debug("calling close shutter $id")
        return shutterClient.close(id)
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
