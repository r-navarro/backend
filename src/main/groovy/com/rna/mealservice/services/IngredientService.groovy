package com.rna.mealservice.services


import com.rna.mealservice.repositories.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class IngredientService {

    @Autowired
    MealRepository mealRepository


    Page<String> searchIngredients(String name, Pageable pageable) {
        if (name.length() > 2) {
            return mealRepository.findByIngredientsLikeIgnoreCase(name, pageable).map {
                it.ingredients
            } as Page<String>
        }
        Page.empty()
    }
}
