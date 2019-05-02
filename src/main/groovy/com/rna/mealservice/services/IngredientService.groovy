package com.rna.mealservice.services

import com.rna.mealservice.repositories.MealRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class IngredientService {

    @Autowired
    MealRepository mealRepository


    Flux<String> searchIngredients(String name) {
        if (name.length() > 2) {
            return mealRepository.findByIngredientsLikeIgnoreCase(name).flatMap {
                Flux.fromIterable(it.ingredients)
            }
        }
        Flux.empty()
    }
}
