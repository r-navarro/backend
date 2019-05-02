package com.rna.mealservice.controllers

import com.rna.mealservice.services.IngredientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class IngredientsController {

    @Autowired
    IngredientService ingredientService

    @GetMapping("/meals/ingredients")
    Flux<String> searchIngredients(@RequestParam("name") String name) {
        ingredientService.searchIngredients(name)
    }
}
