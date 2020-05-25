package com.rna.user

import com.mongodb.client.result.DeleteResult
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import io.reactivex.Single
import io.reactivex.exceptions.CompositeException
import javax.validation.Valid


@Controller("/users")
@Validated
@Secured(SecurityRule.IS_AUTHENTICATED)
class UsersController(private val usersService: UsersService) {

    @Get("/{name}")
    fun getUser(name: String, authentication: Authentication): Single<User> {
        if (authentication.name == name) {
            return usersService.searchRemovePassword(name)
        }
        val roles = authentication.attributes["roles"] as List<*>
        return if ("ROLE_ADMIN" in roles) usersService.searchRemovePassword(name) else throw UnauthorizedException()
    }

    @Get("/me")
    fun getMe(authentication: Authentication): Single<User> {
        return usersService.searchRemovePassword(authentication.name)
    }

    @Get
    @Secured("ROLE_ADMIN")
    fun getUsers(): Single<List<User>> {
        return usersService.findAll()
    }

    @Post
    @Status(HttpStatus.CREATED)
    @Secured("ROLE_ADMIN")
    fun createUser(@Body user: @Valid User): Single<User> {
        return usersService.save(user)
    }

    @Put("/{name}")
    @Status(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    fun update(name: String, @Body user: @Valid User): Single<User> {
        return usersService.update(name, user)
    }

    @Delete("/{name}")
    @Status(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    fun deleteUser(name: String): Single<DeleteResult> {
        return usersService.delete(name)
    }

    @Error
    fun errorHandler(e: Throwable): HttpResponse<ApiError> {
        return when (e) {
            is CompositeException -> HttpResponse.badRequest(ApiError(e.exceptions.map { ce -> ce.localizedMessage }.joinToString(",")))
            is RuntimeException -> HttpResponse.badRequest(ApiError(e.message))
            is UnauthorizedException -> HttpResponse.unauthorized()
            else -> HttpResponse.serverError()
        }
    }
}

data class ApiError(val error: String?)
