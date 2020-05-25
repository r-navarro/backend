package com.rna.event

import com.mongodb.client.result.DeleteResult
import io.reactivex.Single
import java.time.Instant
import javax.inject.Singleton

@Singleton
class EventService(private val eventRepository: EventRepository) {

    fun createEvent(event: Event): Single<Event> {
        event.timestamp = Instant.now().toEpochMilli()
        return eventRepository.save(event)
    }

    fun getEventsByType(type: String): Single<List<Event>> {
        return eventRepository.findByType(type)
    }

    fun getAll(): Single<List<Event>> {
        return eventRepository.list()
    }

    fun deleteByType(type: String): Single<DeleteResult> {
        return eventRepository.deleteEvents(type)
    }

    fun delete(timestamp: Long): Single<DeleteResult> {
        return eventRepository.deleteEvent(timestamp)
    }
}
