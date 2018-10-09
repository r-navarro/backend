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

    Page<Meal> findMeals(Pageable pageable) {
        mealRepository.findAll(pageable).map { ModelsMapper.convert(it) } as Page<Meal>
    }

    Meal updateMeal(Meal mealToUpdate) {
        def meal = mealRepository.findByName(mealToUpdate.name).orElseThrow {
            new MealNotFoundException(mealToUpdate.name)
        }
        meal.name = mealToUpdate.name
        meal.score = mealToUpdate.score
        meal.tags = mealToUpdate.tags
        return ModelsMapper.convert(mealRepository.save(meal))
    }

    void deleteMeal(String name) {
        mealRepository.findByName(name).orElseThrow { new MealNotFoundException(name) }
        mealRepository.deleteByName(name)

    }

    Page<Meal> findMealsByTags(List<String> tags, Pageable pageable) {
        mealRepository.findAllByTagsIn(tags, pageable).map { ModelsMapper.convert(it) } as Page<Meal>
    }
}
