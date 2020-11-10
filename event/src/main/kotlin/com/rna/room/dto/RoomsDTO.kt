package com.rna.room.dto

import java.sql.Timestamp
import java.time.Instant

data class RoomDataDTO(
        var roomId: Int = 0,
        var temperature: Float = 0f,
        var temperatureUnit: String = "",
        var humidity: Float = 0f,
        var humidityUnit: String = "",
        var timestamp: Timestamp = Timestamp.from(Instant.now())
) {
}