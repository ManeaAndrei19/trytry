package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhoneAgenda

fun main(args: Array<String>){
    runApplication<PhoneAgenda>(*args)
}