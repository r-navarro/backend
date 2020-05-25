package com.rna.shutter

import com.rna.shutter.dto.MessageDTO
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.reactivex.Single

@Controller("/shutters")
@Secured(SecurityRule.IS_AUTHENTICATED)
class ShutterController(private val shutterService: ShutterService) {

    @Post("/open/{id}")
    fun open(@PathVariable id: Int): Single<HttpResponse<MessageDTO>> {
        return callService(shutterService.open(id))
    }

    @Post("/close/{id}")
    fun close(@PathVariable id: Int): Single<HttpResponse<MessageDTO>> {
        return callService(shutterService.close(id))
    }

    private fun callService(single: Single<MessageDTO>): Single<HttpResponse<MessageDTO>> {
        return single.map { b ->
            if (b.status != HttpStatus.OK.code) {
                return@map HttpResponse.badRequest(b)
            }
            return@map HttpResponse.ok(b)
        }
    }
}
