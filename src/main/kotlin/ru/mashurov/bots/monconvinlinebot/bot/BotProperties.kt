package ru.mashurov.bots.monconvinlinebot.bot

import org.springframework.beans.factory.annotation.Value

class BotProperties {
    @Value("\${bot.token}")
    lateinit var token: String

    @Value("\${bot.name}")
    lateinit var name: String

    @Value("\${bot.webhookUrl}")
    lateinit var webhookPath: String
}
