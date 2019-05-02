package com.rna.mealservice.services

import com.rna.mealservice.controllers.exceptions.MealNotFoundException
import com.rna.mealservice.documents.MealDocument
import com.rna.mealservice.repositories.MealRepository
import com.rna.mealservice.services.mapper.ModelsMapper
import com.rna.mealservice.services.models.Meal
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@CompileStatic
@Service
class MealService {

    @Autowired
    MealRepository mealRepository

    Mono<Meal> createMeal(Meal meal) {
        def mealDocument = ModelsMapper.convert(meal)
        mealRepository.save(mealDocument).map { ModelsMapper.convert it }
    }

    Mono<Meal> findMeal(String name) {
        mealRepository.findByName(name).switchIfEmpty(notFoundFallback(name)).map { ModelsMapper.convert it }

    }

    Flux<Meal> findMeals(List<String> names, List<String> ingredients) {
        def mealDocuments
        if (names && ingredients) {
            mealDocuments = mealRepository.findAllByIngredientsInOrNameIn(ingredients, names)
        } else if (names) {
            mealDocuments = mealRepository.findAllByNameIn(names)
        } else if (ingredients) {
            mealDocuments = mealRepository.findAllByIngredientsIn(ingredients)
        } else {
            mealDocuments = mealRepository.findAll()
        }
        mealDocuments.map { ModelsMapper.convert it }
    }

    Mono<Meal> updateMeal(Meal mealToUpdate) {
        mealRepository.findByName(mealToUpdate.name)
                .switchIfEmpty(notFoundFallback(mealToUpdate.name))
                .doOnSuccess {
            it.name = mealToUpdate.name
            it.score = mealToUpdate.score
            it.ingredients = mealToUpdate.ingredients
            mealRepository.save(it).subscribe()
        }.map { ModelsMapper.convert it }

    }

    Mono<Void> deleteMeal(String name) {
        mealRepository.findByName(name)
                .switchIfEmpty(Mono.error(new MealNotFoundException(name)))
                .flatMap {
            mealRepository.delete(it).then()
        }
    }

    Flux<Meal> searchMealNames(String name, Pageable pageable) {
        if (name.length() > 2) {
            return mealRepository.findByNameLikeIgnoreCase(name, pageable).map {
                ModelsMapper.convert(it)
            }
        }
        Flux.empty()
    }

    static Mono<MealDocument> notFoundFallback(String name) {
        Mono.error(new MealNotFoundException(name))
    }
}
