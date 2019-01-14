package com.rna.mealservice.controllers


import com.rna.mealservice.services.IngredientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class IngredientsController {

    @Autowired
    IngredientService ingredientService

    @GetMapping("/meals/ingredients")
    Page<String> searchIngredients(@RequestParam("name") String name, Pageable pageable) {
        ingredientService.searchIngredients(name, pageable)
    }
}
