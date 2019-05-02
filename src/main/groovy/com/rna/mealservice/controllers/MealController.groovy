package com.rna.mealservice.controllers

import com.rna.mealservice.controllers.dtos.MealDto
import com.rna.mealservice.controllers.mapper.DtoMapper
import com.rna.mealservice.services.MealService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import javax.validation.Valid

@CompileStatic
@RestController
class MealController {

    @Autowired
    MealService mealService

    @PostMapping("/meals")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<MealDto> createMeal(@Valid @RequestBody MealDto mealDto) {
        mealService.createMeal(DtoMapper.convert(mealDto)).map { DtoMapper.convert it }

    }

    @GetMapping("/meals/{id}")
    Mono<MealDto> findMeal(@PathVariable("id") String id) {
        mealService.findMeal(id).map { DtoMapper.convert it }
    }

    @GetMapping("/meals/search")
    Flux<MealDto> searchMeal(@RequestParam("name") String name) {
        mealService.searchMealNames(name, null).map { DtoMapper.convert(it) }
    }

    @GetMapping("/meals")
    Flux<MealDto> findMeals(
            @RequestParam(name = 'names', required = false) List<String> names,
            @RequestParam(name = 'ingredients', required = false) List<String> ingredients) {
        mealService.findMeals(names, ingredients).map {
            DtoMapper.convert(it)
        }
    }

    @PutMapping("/meals")
    Mono<MealDto> updateMeal(@Valid @RequestBody MealDto mealDto) {
        mealService.updateMeal(DtoMapper.convert(mealDto)).map { DtoMapper.convert it }
    }

    @DeleteMapping("/meals/{id}")
    Mono<Void> deleteMeal(@PathVariable("id") String id) {
        mealService.deleteMeal(id)
    }
}
