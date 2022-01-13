package ru.mashurov.bots.monconvinlinebot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class MonConvInlineBotApplication

fun main() {
    runApplication<MonConvInlineBotApplication>()
}
