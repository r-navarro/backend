package com.rna.room

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Single
import org.slf4j.LoggerFactory

@Controller("/rooms")
@Secured(SecurityRule.IS_AUTHENTICATED)
class RoomsController(private val roomsClient: RoomsClient) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val log = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    @Get("/{id}")
    fun getData(@PathVariable id: Int): Single<HttpResponse<*>> {
        log.debug("call get data for room $id")
        return roomsClient.call(id)
    }
}