package com.rna.event

data class EventDTO(
        var type: String,
        var sender: String,
        var timestamp: Long) {
}
