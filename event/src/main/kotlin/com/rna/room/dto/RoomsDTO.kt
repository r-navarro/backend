package com.rna.room.dto

data class RoomDataDTO(
        var roomId: Int = 0,
        var temperature: Float = 0f,
        var temperatureUnit: String = "",
        var humidity: Float = 0f,
        var humidityUnit: String = "",
        var timestamp: Long = 0
) {
}