package com.rna.shutter

import com.rna.shutter.dto.ShutterDTO
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single

@Client(id = "shutters", path = "/shutters")
interface ShutterClient {

    @Post("/")
    fun call(@Body shutterDTO: ShutterDTO): Single<HttpResponse<*>>
}
