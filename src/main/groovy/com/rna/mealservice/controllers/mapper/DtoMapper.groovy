package com.rna.mealservice.controllers.mapper

import com.rna.mealservice.controllers.dtos.MealDto
import com.rna.mealservice.services.models.Meal

class DtoMapper {

    static Meal convert(MealDto source) {
        new Meal(name: source?.name, score: source?.score, ingredients: source?.ingredients, recipe: source?.recipe)
    }

    static MealDto convert(Meal source) {
        new MealDto(name: source?.name, score: source?.score, ingredients: source?.ingredients, recipe: source?.recipe)
    }

}
