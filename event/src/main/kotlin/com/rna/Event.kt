package com.rna

import io.micronaut.runtime.Micronaut

object Event {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("")
                .mainClass(Event.javaClass)
                .start()
    }
}
