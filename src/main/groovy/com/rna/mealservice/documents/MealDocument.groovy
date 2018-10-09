package com.rna.mealservice.documents

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import java.time.LocalDateTime

@Document
class MealDocument {

    @Id
    @Indexed(unique = false)
    String name
    Integer score
    List<String> tags
    @CreatedDate
    LocalDateTime creationDate
    @LastModifiedDate
    LocalDateTime lastModifiedDate
}
