package com.rna.event

import com.mongodb.client.result.DeleteResult
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import io.reactivex.Single
import io.reactivex.exceptions.CompositeException
import javax.validation.Valid


@Controller("/events")
@Validated
@Secured(SecurityRule.IS_AUTHENTICATED)
class EventController(private val eventService: EventService) {

    @Get("/{type}")
    fun getByType(type: String): Single<List<Event>> {
        return eventService.getEventsByType(type)
    }

    @Get
    fun getAll(): Single<List<Event>> {
        return eventService.getAll()
    }

    @Post
    @Status(HttpStatus.CREATED)
    fun createEvent(@Body @Valid event: Event): Single<Event> {
        return eventService.createEvent(event)
    }

    @Delete("/{type}")
    @Status(HttpStatus.NO_CONTENT)
    fun deleteByType(type: String): Single<DeleteResult> {
        return eventService.deleteByType(type)
    }

    @Delete("/one/{timestamp}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(timestamp: Long): Single<DeleteResult> {
        return eventService.delete(timestamp)
    }

    @Error
    fun handleError(e: Throwable): HttpResponse<ApiError> {
        return when (e) {
            is CompositeException -> HttpResponse.badRequest(ApiError(e.exceptions.joinToString(",") { ce -> ce.localizedMessage }))
            is RuntimeException -> HttpResponse.badRequest(ApiError(e.message))
            else -> HttpResponse.serverError()
        }
    }
}
