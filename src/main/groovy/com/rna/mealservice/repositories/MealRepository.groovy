package com.rna.mealservice.repositories

import com.rna.mealservice.documents.MealDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MealRepository extends ReactiveCrudRepository<MealDocument, String> {

    Mono<MealDocument> findByName(String name)

    Flux<MealDocument> findAllByIngredientsIn(List<String> ingredients)

    Flux<MealDocument> findAllByNameIn(List<String> names)

    Mono<Void> deleteByName(String name)

    Flux<MealDocument> findByNameLikeIgnoreCase(String name, Pageable pageable)

    Flux<MealDocument> findAllByIngredientsInOrNameIn(List<String> ingredients, List<String> names)

    @Query("{'ingredients' : {\$regex: ?0, \$options: 'i'}}")
    Flux<MealDocument> findByIngredientsLikeIgnoreCase(String name)
}
