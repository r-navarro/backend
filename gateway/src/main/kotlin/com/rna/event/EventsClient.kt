package com.rna.event

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client
import io.micronaut.retry.annotation.Retryable
import io.reactivex.Single
import java.net.UnknownHostException
import javax.validation.Valid

@Client(id = "events", path = "events")
interface EventsClient {

    @Post("/open/{id}")
    @Retryable(multiplier = "1.5", excludes = [UnknownHostException::class])
    fun open(@PathVariable id: Int): Single<HttpResponse<*>>

    @Post("/close/{id}")
    @Retryable(multiplier = "1.5", excludes = [UnknownHostException::class])
    fun close(@PathVariable id: Int): Single<HttpResponse<*>>

    @Get("/{type}")
    fun getByType(type: String): Single<HttpResponse<*>>

    @Get
    fun getAll(): Single<HttpResponse<*>>

    @Post
    @Status(HttpStatus.CREATED)
    fun createEvent(@Body @Valid event: EventDTO): Single<HttpResponse<*>>

    @Delete("/{type}")
    @Status(HttpStatus.NO_CONTENT)
    fun deleteByType(type: String): Single<HttpResponse<*>>

    @Delete("/one/{timestamp}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(timestamp: Long): Single<HttpResponse<*>>
}
