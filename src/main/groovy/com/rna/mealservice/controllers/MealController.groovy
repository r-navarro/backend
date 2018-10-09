package com.rna.mealservice.controllers


import com.rna.mealservice.controllers.dtos.MealDto
import com.rna.mealservice.controllers.mapper.DtoMapper
import com.rna.mealservice.services.MealService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
class MealController {

    @Autowired
    MealService mealService

    @PostMapping("/meals")
    @ResponseStatus(HttpStatus.CREATED)
    MealDto createMeal(@Valid @RequestBody MealDto mealDto) {
        def meal = mealService.createMeal(DtoMapper.convert(mealDto))
        DtoMapper.convert(meal)
    }

    @GetMapping("/meals/{id}")
    MealDto findMeal(@PathVariable("id") String id) {
        DtoMapper.convert(mealService.findMeal(id))
    }

    @GetMapping("/meals")
    Page<MealDto> findMeals(Pageable pageable, @RequestParam(required = false) List<String> tags) {
        if (tags) {
            return mealService.findMealsByTags(tags, pageable).map { DtoMapper.convert(it) } as Page<MealDto>
        }
        mealService.findMeals(pageable).map { DtoMapper.convert(it) } as Page<MealDto>
    }

    @PutMapping("/meals")
    MealDto updateMeal(@Valid @RequestBody MealDto mealDto) {
        def update = mealService.updateMeal(DtoMapper.convert(mealDto))
        DtoMapper.convert(update)
    }

    @DeleteMapping("/meals/{id}")
    void deleteMeal(@PathVariable("id") String id) {
        mealService.deleteMeal(id)
    }
}
