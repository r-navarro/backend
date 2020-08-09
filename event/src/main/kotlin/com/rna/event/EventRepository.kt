package com.rna.event

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts.descending
import com.mongodb.client.model.Sorts.orderBy
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class EventRepository(val mongoClient: MongoClient) {

    private val collection: MongoCollection<Event>
        get() = mongoClient.getDatabase("test").getCollection("event", Event::class.java)

    fun save(event: Event): Single<Event> {
        return Single.fromPublisher<InsertOneResult>(collection.insertOne(event)).map { event }
    }

    fun findByType(type: String): Single<List<Event>> {
        return Flowable.fromPublisher(collection.find(Filters.eq("type", type)).sort(orderBy(descending("timestamp")))).toList()
    }

    fun list(): Single<List<Event>> {
        return Flowable.fromPublisher(collection.find().sort(orderBy(descending("timestamp")))).toList()
    }

    fun deleteEvents(type: String): Single<DeleteResult> {
        return Single.fromPublisher<DeleteResult>(collection.deleteMany(Filters.eq("type", type)))
    }

    fun deleteEvent(timestamp: Long): Single<DeleteResult> {
        return Single.fromPublisher<DeleteResult>(collection.deleteOne(Filters.eq("timestamp", timestamp)))
    }

}
