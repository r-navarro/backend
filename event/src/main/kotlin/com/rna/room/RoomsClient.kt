package com.rna.room

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single

@Client(id = "rooms", path = "/rooms")
interface RoomsClient {

    @Get("/{id}")
    fun call(@PathVariable id: Int): Single<HttpResponse<*>>
}