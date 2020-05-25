package com.rna.meal

import com.mongodb.client.result.DeleteResult
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.validation.Validated
import io.reactivex.Single
import io.reactivex.exceptions.CompositeException
import javax.validation.Valid

@Controller("/meals")
@Validated
@Secured("isAuthenticated()")
class MealsController(private val mealsService: MealsService) {

    @Get("/{name}")
    fun getMeal(name: String): Single<Meal> {
        return mealsService.search(QueryWrapper(name))
    }

    @Get
    fun getMeals(): Single<List<Meal>> {
        return mealsService.findAll()
    }

    @Post
    @Status(HttpStatus.CREATED)
    fun createMeal(@Body meal: @Valid Meal): Single<Meal> {
        return mealsService.save(meal)
    }

    @Put("/{name}")
    @Status(HttpStatus.NO_CONTENT)
    fun update(name: String, @Body meal: @Valid Meal): Single<Meal> {
        return mealsService.update(name, meal)
    }

    @Delete("/{name}")
    @Status(HttpStatus.NO_CONTENT)
    fun deleteMeal(name: String): Single<DeleteResult> {
        return mealsService.delete(name)
    }

    @Error
    fun errorHandler(e: Throwable): HttpResponse<ApiError> {
        return when (e) {
            is CompositeException -> HttpResponse.badRequest(ApiError(e.exceptions.map { ce -> ce.localizedMessage }.joinToString ( "," )))
            is RuntimeException -> HttpResponse.badRequest(ApiError(e.message))
            else -> HttpResponse.serverError()
        }
    }
}
