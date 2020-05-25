package com.rna.meal

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@Introspected
data class Meal(
        @field:NotEmpty(message = "A meal need a name")
        var name: String,
        @field:NotEmpty(message = "A meal need a recipe")
        @field:NotNull(message = "A meal need a recipe")
        var recipe: String,
        @field:PositiveOrZero(message = "A meal need a score")
        var score: Int,
        @field:NotEmpty(message = "A meal need ingredients")
        var ingredients: List<String> = emptyList())

data class QueryWrapper(val name: String)

data class ApiError(val error: String?)
