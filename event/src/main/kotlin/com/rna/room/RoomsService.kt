package com.rna.room

import com.rna.room.dto.RoomDataDTO
import io.micronaut.context.annotation.Property
import io.micronaut.scheduling.annotation.Scheduled
import io.reactivex.Single
import org.slf4j.LoggerFactory
import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Singleton


@Singleton
class RoomsService(
        private val roomsClient: RoomsClient,
        @Property(name = "rooms.retention") val roomsDataRetention: Long,
        @Property(name = "rooms.ids") val roomsId: List<Int>,
        private val roomsRepository: RoomsRepository
) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val log = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    @Scheduled(fixedDelay = "1h")
    fun fetchDataForRooms() {
        roomsId.map { roomId ->
            roomsClient.call(roomId)
                    .doOnSuccess { httpResponse ->
                        val body = httpResponse.body() as LinkedHashMap<*, *>
                        try {
                            val roomDataDTO = RoomDataDTO(timestamp = Timestamp.from(Instant.now()), roomId = roomId)
                            roomDataDTO.temperature = body["temperature"].toString().toFloat()
                            roomDataDTO.temperatureUnit = body["temperatureUnit"].toString()
                            roomDataDTO.humidity = body["humidity"].toString().toFloat()
                            roomDataDTO.humidityUnit = body["humidityUnit"].toString()
                            persistRoomData(roomDataDTO)
                        } catch (e: Exception) {
                            log.error("Cannot read data for room $roomId : ${e.message}")
                        }
                    }
                    .doOnError {
                        log.error("Cannot reach data for room $roomId")
                    }
                    .subscribe()
        }
    }

    fun getRoomsData(): Single<MutableList<RoomDataDTO>>? {
        log.debug("Read all rooms data from DB")
        return roomsRepository.getRoomsData()
    }

    private fun persistRoomData(roomDataDTO: RoomDataDTO) {
        val minus = roomDataDTO.timestamp.toInstant().minus(roomsDataRetention, ChronoUnit.DAYS)
        roomsRepository.deleteRoomData(Timestamp.from(minus))
                .map {
                    if (!it.wasAcknowledged()) {
                        log.error("Could not delete data for room ${roomDataDTO.roomId}")
                    }
                }.subscribe()
        roomsRepository.addRoomData(roomDataDTO)
                .doOnSuccess { log.debug("Data inserted for room ${roomDataDTO.roomId}") }
                .doOnError { log.error("Data not inserted for room ${roomDataDTO.roomId}") }
                .subscribe()
    }
}