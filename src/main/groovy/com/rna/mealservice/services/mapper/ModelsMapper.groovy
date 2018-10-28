package com.rna.mealservice.services.mapper

import com.rna.mealservice.documents.MealDocument
import com.rna.mealservice.services.models.Meal

class ModelsMapper {
    static Meal convert(MealDocument source) {
        new Meal(name: source?.name, score: source?.score, ingredients: source?.ingredients, recipe: source?.recipe)
    }

    static MealDocument convert(Meal source) {
        new MealDocument(name: source?.name, score: source?.score, ingredients: source?.ingredients, recipe: source?.recipe)
    }

}
