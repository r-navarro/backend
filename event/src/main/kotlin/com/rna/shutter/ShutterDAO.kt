package com.rna.shutter

import io.micronaut.http.HttpResponse
import io.reactivex.Single

interface ShutterDAO {

    fun open(id: Int): Single<HttpResponse<*>>

    fun close(id: Int): Single<HttpResponse<*>>
}
