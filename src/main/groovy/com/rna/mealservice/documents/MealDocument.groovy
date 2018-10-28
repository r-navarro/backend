package com.rna.mealservice.documents

import groovy.transform.EqualsAndHashCode
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import java.time.LocalDateTime

@Document
@EqualsAndHashCode(includes = 'name')
class MealDocument {

    @Id
    String name
    Integer score
    @Indexed
    List<String> ingredients
    String recipe
    @CreatedDate
    LocalDateTime creationDate
    @LastModifiedDate
    LocalDateTime lastModifiedDate
}
