package com.rna

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("com.rna")
                .mainClass(Application.javaClass)
                .start()
    }
}
