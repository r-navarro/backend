package com.rna.mealservice.controllers.dtos

import javax.validation.constraints.NotNull

class MealDto {

    @NotNull(message = "Name cannot be empty")
    String name
    @NotNull(message = "Score cannot be empty")
    Integer score
    String recipe
    List<String> ingredients
}
