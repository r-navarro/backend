package com.rna.mealservice.services

import com.rna.mealservice.controllers.exceptions.MealNotFoundException
import com.rna.mealservice.repositories.MealRepository
import com.rna.mealservice.services.mapper.ModelsMapper
import com.rna.mealservice.services.models.Meal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MealService {

    @Autowired
    MealRepository mealRepository

    Meal createMeal(Meal meal) {
        def mealDocument = ModelsMapper.convert(meal)
        def savedMeal = mealRepository.save(mealDocument)

        ModelsMapper.convert(savedMeal)
    }

    Meal findMeal(String name) {
        def meal = mealRepository.findByName(name).orElseThrow { new MealNotFoundException(name) }
        ModelsMapper.convert(meal)
    }

    Page<Meal> findMeals(List<String> names, List<String> ingredients, Pageable pageable) {
        def mealDocuments
        if (names && ingredients) {
            mealDocuments = mealRepository.findAllByIngredientsInOrNameIn(ingredients, names, pageable)
        } else if (names) {
            mealDocuments = mealRepository.findAllByNameIn(names, pageable)
        } else if (ingredients) {
            mealDocuments = mealRepository.findAllByIngredientsIn(ingredients, pageable)
        } else {
            mealDocuments = mealRepository.findAll(pageable)
        }
        mealDocuments.map { ModelsMapper.convert(it) } as Page<Meal>
    }

    Meal updateMeal(Meal mealToUpdate) {
        def meal = mealRepository.findByName(mealToUpdate.name).orElseThrow {
            new MealNotFoundException(mealToUpdate.name)
        }
        meal.name = mealToUpdate.name
        meal.score = mealToUpdate.score
        meal.ingredients = mealToUpdate.ingredients
        return ModelsMapper.convert(mealRepository.save(meal))
    }

    void deleteMeal(String name) {
        mealRepository.findByName(name).orElseThrow { new MealNotFoundException(name) }
        mealRepository.deleteByName(name)

    }

    Page<Meal> searchMealNames(String name, Pageable pageable) {
        if (name.length() > 2) {
            return mealRepository.findByNameLikeIgnoreCase(name, pageable).map {
                ModelsMapper.convert(it)
            } as Page<Meal>
        }
        Page.empty()
    }
}
