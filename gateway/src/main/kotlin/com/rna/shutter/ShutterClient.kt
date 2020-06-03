package com.rna.shutter

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.retry.annotation.Retryable
import io.reactivex.Single
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

@Client(id = "shutters", path = "shutters")
interface ShutterClient {

    @Post("/open/{id}")
    @Retryable(multiplier = "1.5", excludes = [UnknownHostException::class])
    fun open(@PathVariable id: Int): Single<HttpResponse<*>>

    @Post("/close/{id}")
    @Retryable(multiplier = "1.5", excludes = [UnknownHostException::class])
    fun close(@PathVariable id: Int): Single<HttpResponse<*>>
}
