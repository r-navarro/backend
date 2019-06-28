package com.rna.meal

import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import javax.inject.Inject
import javax.inject.Singleton
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.Success
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Singleton
class MealsRepository {

    @Inject
    private val mongoClient: MongoClient? = null

    private val collection: MongoCollection<Meal>
        get() = mongoClient!!.getDatabase("test").getCollection("meal", Meal::class.java)

    fun save(meal: Meal): Single<Meal> {
        return Single.fromPublisher<Success>(collection.insertOne(meal)).map { meal }
    }

    fun find(id: String): Maybe<Meal> {
        return Flowable.fromPublisher(collection.find(Filters.eq("name", id)).limit(1)).firstElement()
    }

    fun list(): Single<List<Meal>> {
        return Flowable.fromPublisher(collection.find()).toList()
    }

    fun delete(id: String): Single<Void> {
        return Single.fromPublisher<DeleteResult>(collection.deleteOne(Filters.eq("name", id))).map {  }
    }
}
