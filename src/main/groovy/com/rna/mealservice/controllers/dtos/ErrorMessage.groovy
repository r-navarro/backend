package com.rna.mealservice.controllers.dtos

import java.time.LocalDateTime

class ErrorMessage {
    String message
    LocalDateTime time = LocalDateTime.now()
}
