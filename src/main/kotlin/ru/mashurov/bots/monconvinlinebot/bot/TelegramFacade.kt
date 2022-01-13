package ru.mashurov.bots.monconvinlinebot.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class TelegramFacade {
    @Autowired
    lateinit var changer: CurrencyChanger

    fun handleInlineQuery(update: Update): BotApiMethod<*>? {
        if (update.hasInlineQuery()) {
            val inlQueryParams = update.inlineQuery.query.split(" ")
            if (inlQueryParams.size == 2 && inlQueryParams[1].length == 3) {
                return when (inlQueryParams[1]) {
                    "usd" -> AnswerInlineQuery(
                        update.inlineQuery.id,
                        changer.change(inlQueryParams[0].toDouble())
                    )
                    else -> AnswerInlineQuery(
                        update.inlineQuery.id,
                        changer.change(inlQueryParams[0].toDouble(), inlQueryParams[1])
                    )
                }
            }
        }

        return null
    }
}