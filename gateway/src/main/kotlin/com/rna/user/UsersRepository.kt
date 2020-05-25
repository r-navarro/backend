package com.rna.user

import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class UsersRepository(private val mongoClient: MongoClient) {

    private val collection: MongoCollection<User>
        get() = mongoClient.getDatabase("test").getCollection("user", User::class.java)

    fun save(user: User): Single<User> {
        return Single.fromPublisher(collection.insertOne(user)).map { user }
    }

    fun find(id: String): Single<User> {
        return Single.fromPublisher(collection.find(Filters.eq("name", id)).limit(1).first())
    }

    fun list(): Single<List<User>> {
        return Flowable.fromPublisher(collection.find()).toList()
    }

    fun delete(id: String): Single<DeleteResult> {
        return Single.fromPublisher(collection.deleteOne(Filters.eq("name", id)))
    }

    fun update(user: User): Single<User> {
        return Single.fromPublisher(
                collection.replaceOne(
                        Filters.eq("name", user.name),
                        user)
        ).map { user }
    }

}
