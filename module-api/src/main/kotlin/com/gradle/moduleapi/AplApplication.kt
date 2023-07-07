package com.gradle.moduleapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AplApplication

fun main(args: Array<String>) {
    runApplication<AplApplication>(*args)
}
