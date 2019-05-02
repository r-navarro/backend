package com.rna.mealservice.controllers

import com.rna.mealservice.controllers.dtos.ErrorMessage
import com.rna.mealservice.controllers.exceptions.MealNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MealNotFoundException)
    @ResponseBody
    def handleMealNotFound(MealNotFoundException e){
        new ErrorMessage(message:e.message)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException)
    @ResponseBody
    def handleUserNotFound(UsernameNotFoundException e){
        new ErrorMessage(message:e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException)
    @ResponseBody
    def handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        def result = e.getBindingResult();
        def fieldErrors = result.getFieldErrors()
        def messages = []
        fieldErrors.each {
            messages << "$it.defaultMessage"
        }
        new ErrorMessage(message:messages.join(','))
    }

}
