package com.rna.mealservice.repositories

import com.rna.mealservice.documents.MealDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface MealRepository extends PagingAndSortingRepository<MealDocument, String> {

    Optional<MealDocument> findByName(String name)

    Page<MealDocument> findAllByTagsIn(List<String> tags, Pageable pageable)

    void deleteByName(String name)
}
