package com.rna.mealservice.repositories

import com.rna.mealservice.documents.MealDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface MealRepository extends PagingAndSortingRepository<MealDocument, String> {

    Optional<MealDocument> findByName(String name)

    Page<MealDocument> findAllByIngredientsIn(List<String> ingredients, Pageable pageable)

    Page<MealDocument> findAllByNameIn(List<String> names, Pageable pageable)

    void deleteByName(String name)

    Page<MealDocument> findByNameLikeIgnoreCase(String name, Pageable pageable)

    Page<MealDocument> findAllByIngredientsInOrNameIn(List<String> ingredients, List<String> names, Pageable pageable)

    @Query("{'ingredients' : {\$regex: ?0, \$options: 'i'}}")
    Page<MealDocument> findByIngredientsLikeIgnoreCase(String name, Pageable pageable)
}
