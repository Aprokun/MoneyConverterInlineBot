package ru.mashurov.bots.monconvinlinebot.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.mashurov.bots.monconvinlinebot.bot.Bot

@RestController
class BotController {
    @Autowired
    lateinit var bot: Bot

    @PostMapping("/")
    fun webhook(@RequestBody update: Update): BotApiMethod<*>? = bot.onWebhookUpdateReceived(update)
}