package com.rna.mealservice.controllers.exceptions

class MealNotFoundException extends RuntimeException {

    MealNotFoundException(String s) {
        super(s)
    }
}
