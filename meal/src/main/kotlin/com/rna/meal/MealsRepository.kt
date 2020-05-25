package com.rna.meal

import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.Success
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class MealsRepository(val mongoClient: MongoClient) {

    private val collection: MongoCollection<Meal>
        get() = mongoClient.getDatabase("test").getCollection("meal", Meal::class.java)

    fun save(meal: Meal): Single<Meal> {
        return Single.fromPublisher<Success>(collection.insertOne(meal)).map { meal }
    }

    fun find(id: String): Single<Meal> {
        return Single.fromPublisher(collection.find(Filters.eq("name", id)).limit(1).first())
    }

    fun list(): Single<List<Meal>> {
        return Flowable.fromPublisher(collection.find()).toList()
    }

    fun delete(id: String): Single<DeleteResult> {
        return Single.fromPublisher<DeleteResult>(collection.deleteOne(Filters.eq("name", id)))
    }

    fun update(meal: Meal): Single<Meal> {
        return Single.fromPublisher<UpdateResult>(
                collection.replaceOne(
                        Filters.eq("name", meal.name),
                        meal)
        ).map { meal }
    }
}
