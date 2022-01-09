package ru.mashurov.bots.monconvinlinebot.bot

import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.starter.SpringWebhookBot

class Bot : SpringWebhookBot {
    private val telegramFacade: TelegramFacade

    var name: String = ""
    var token: String = ""
    var path: String = ""

    constructor(setWebhook: SetWebhook?, telegramFacade: TelegramFacade) : super(setWebhook) {
        this.telegramFacade = telegramFacade
    }

    constructor(options: DefaultBotOptions?, setWebhook: SetWebhook?, telegramFacade: TelegramFacade) : super(options,
        setWebhook) {
        this.telegramFacade = telegramFacade
    }

    override fun onWebhookUpdateReceived(update: Update): BotApiMethod<*>? {
        return telegramFacade.handleInlineQuery(update)
    }

    override fun getBotUsername(): String = name

    override fun getBotToken(): String = token

    override fun getBotPath(): String = path
}