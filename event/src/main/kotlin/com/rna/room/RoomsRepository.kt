package com.rna.room

import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import com.rna.room.dto.RoomDataDTO
import io.reactivex.Flowable
import io.reactivex.Single
import java.sql.Timestamp
import javax.inject.Singleton

@Singleton
class RoomsRepository(private val mongoClient: MongoClient) {

    private val collection: MongoCollection<RoomDataDTO>
        get() = mongoClient.getDatabase("test").getCollection("rooms", RoomDataDTO::class.java)

    fun addRoomData(roomDataDTO: RoomDataDTO): Single<RoomDataDTO> {
        return Single.fromPublisher(collection.insertOne(roomDataDTO)).map { roomDataDTO }
    }

    fun deleteRoomData(timestamp: Timestamp): Single<DeleteResult> {
        return Single.fromPublisher(collection.deleteMany(Filters.lte("timestamp", timestamp)))
    }

    fun getRoomData(roomId: Int): Single<MutableList<RoomDataDTO>>? {
        return Flowable.fromPublisher(collection.find(Filters.eq("roomID", roomId))).toList()
    }

    fun getRoomsData(): Single<MutableList<RoomDataDTO>>? {
        return Flowable.fromPublisher(collection.find()).toList()
    }
}