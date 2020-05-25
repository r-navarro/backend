package com.rna

import io.micronaut.runtime.Micronaut

object Meal {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("com.rna")
                .mainClass(Meal.javaClass)
                .start()
    }
}
