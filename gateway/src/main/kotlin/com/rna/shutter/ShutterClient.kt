package com.rna.shutter

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single

@Client(id = "shutters", path = "shutters")
interface ShutterClient {

    @Post("/open/{id}")
    fun open(@PathVariable id: Int): Single<HttpResponse<*>>

    @Post("/close/{id}")
    fun close(@PathVariable id: Int): Single<HttpResponse<*>>
}
