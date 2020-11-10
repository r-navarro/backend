package com.rna.room

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import io.micronaut.retry.annotation.Retryable
import io.reactivex.Single
import java.net.UnknownHostException

@Client(id = "rooms", path = "rooms")
interface RoomClient {

    @Get("/{id}")
    @Retryable(multiplier = "1.5", excludes = [UnknownHostException::class])
    fun getRoomsData(@PathVariable id: Int): Single<HttpResponse<*>>

    @Get("/")
    @Retryable(multiplier = "1.5", excludes = [UnknownHostException::class])
    fun getAllRoomsData(): Single<HttpResponse<*>>
}
