package com.rna

import io.micronaut.runtime.Micronaut

object Gateway {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("com.rna")
                .mainClass(Gateway.javaClass)
                .start()
    }
}
