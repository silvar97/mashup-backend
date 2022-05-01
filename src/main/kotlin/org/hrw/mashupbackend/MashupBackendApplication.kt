package org.hrw.mashupbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class MashupBackendApplication

fun main(args: Array<String>) {
    runApplication<MashupBackendApplication>(*args)

}

