package com.rna.endpoint

import com.rna.meal.ApiError
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Produces
@Singleton
@Requires(classes = [RuntimeException::class, ExceptionHandler::class])
class ExceptionHandler:ExceptionHandler<RuntimeException, HttpResponse<ApiError>> {

    override fun handle(request: HttpRequest<*>?, exception: RuntimeException?): HttpResponse<ApiError> {
        return HttpResponse.badRequest(ApiError(exception?.message))
    }
}
